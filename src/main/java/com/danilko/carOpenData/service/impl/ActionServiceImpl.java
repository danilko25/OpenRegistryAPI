package com.danilko.carOpenData.service.impl;

import com.danilko.carOpenData.dto.mapper.toDto.FilterDtoMapper;
import com.danilko.carOpenData.dto.mapper.toDto.VehicleReadDtoMapper;
import com.danilko.carOpenData.dto.readDto.ActionReadDto;
import com.danilko.carOpenData.dto.mapper.toDto.ActionReadDtoMapper;
import com.danilko.carOpenData.dto.readDto.VehicleReadDto;
import com.danilko.carOpenData.dto.utilDto.ActionFilterDto;
import com.danilko.carOpenData.exception.EntityNotFoundException;
import com.danilko.carOpenData.repository.*;

import com.danilko.carOpenData.service.ActionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DateTimeException;
import java.time.Year;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ActionServiceImpl implements ActionService {
    private final ActionRepository actionRepository;
    private final ActionReadDtoMapper actionReadDtoMapper;
    private final VehicleReadDtoMapper vehicleReadDtoMapper;
    private final StatisticFilterRepository statisticFilterRepository;
    private final FilterDtoMapper filterDtoMapper;


    @Override
    public Optional<ActionReadDto> findByNumberPlate(String numberPlate) {
        numberPlate = changeEnglishToCyrillic(numberPlate);
        var action = actionRepository.findByNumberPlate(numberPlate);
        return action.map(actionReadDtoMapper::mapFrom);
    }


    @Override
    public List<ActionReadDto> findAllByVehicleVin(String vin) {
        return actionRepository.findAllByVehicleVin(vin).stream().map(actionReadDtoMapper::mapFrom).toList();
    }

    @Override
    public List<VehicleReadDto> countAll(ActionFilterDto actionFilterDto) {


        var actions = actionRepository.findAll(new ActionSpecification(actionFilterDto));

        Set<VehicleReadDto> vehiclesSet = new HashSet<>();
        actions.forEach(action -> vehiclesSet.add(vehicleReadDtoMapper.mapFrom(action.getVehicle())));

        return new ArrayList<>(vehiclesSet);
    }


    @Override
    public List<ActionReadDto> findAll(ActionFilterDto actionFilterDto) {
        if (actionFilterDto.getNumPlate().isPresent()){
            actionFilterDto.setNumPlate(Optional.of(changeEnglishToCyrillic(actionFilterDto.getNumPlate().get())));
        }
        var actions = actionRepository.findAll(new ActionSpecification(actionFilterDto));
        return actions.stream().map(actionReadDtoMapper::mapFrom).toList();
    }

    @Override
    public List<ActionReadDto> findAllByStatisticFilter(Long statisticFilterId) {
        var statisticFilter = statisticFilterRepository.findById(statisticFilterId)
                .orElseThrow(() -> new EntityNotFoundException("Statistic Filter with id = " + statisticFilterId + " not exist"));

        var actionFilter = filterDtoMapper.mapFrom(statisticFilter);
        var actions = actionRepository.findAll(new ActionSpecification(actionFilter));
        return actions.stream().map(actionReadDtoMapper::mapFrom).toList();

    }

    @Override
    public void deleteAllByRegDateYear(Integer regDateYear) {
        try{
            var year = Year.of(regDateYear);
            actionRepository.deleteAllByRegDateIsBetween(year.atDay(1), year.atDay(year.length()));
        } catch (DateTimeException e) {
            throw new EntityNotFoundException("Reg Date Year = " + regDateYear + " not exist");
        }
    }

    private String changeEnglishToCyrillic(String text){
        String engChars = "ETIOPAHKXCBM";
        String cyrillicChars = "ЕТІОРАНКХСВМ";
        for (int i = 0; i<engChars.length(); i++){
            text = text.replace(engChars.charAt(i), cyrillicChars.charAt(i));
        }

        return text;
    }


}
