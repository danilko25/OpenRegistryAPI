package com.danilko.carOpenData.service.impl;


import com.danilko.carOpenData.dto.mapper.toDto.VehicleReadDtoMapper;
import com.danilko.carOpenData.dto.readDto.VehicleReadDto;
import com.danilko.carOpenData.dto.utilDto.ActionFilterDto;
import com.danilko.carOpenData.entity.Vehicle;
import com.danilko.carOpenData.repository.ActionRepository;
import com.danilko.carOpenData.repository.ActionSpecification;
import com.danilko.carOpenData.repository.VehicleRepository;
import com.danilko.carOpenData.service.VehicleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository vehicleRepository;
    private final VehicleReadDtoMapper vehicleReadDtoMapper;
    private final ActionRepository actionRepository;

    @Override
    public Optional<VehicleReadDto> findByVin(String vin) {
        return vehicleRepository.findByVin(vin).map(vehicleReadDtoMapper::mapFrom);
    }

    @Override
    public List<VehicleReadDto> findAll(ActionFilterDto actionFilterDto) {
        if (actionFilterDto.getNumPlate().isPresent()){
            actionFilterDto.setNumPlate(Optional.of(changeEnglishToCyrillic(actionFilterDto.getNumPlate().get())));
        }
        var actions = actionRepository.findAll(new ActionSpecification(actionFilterDto));
        Set<VehicleReadDto> vehiclesSet = new HashSet<>();
        actions.forEach(action -> vehiclesSet.add(vehicleReadDtoMapper.mapFrom(action.getVehicle())));
        return vehiclesSet.stream().toList();
    }

    private String changeEnglishToCyrillic(String text){
        String engChars = "ETIOPAHKXCBM";
        String cyrillicChars = "ЕТІОРАНКХСВМ";
        for (int i = 0; i<engChars.length(); i++){
            text = text.replace(engChars.charAt(i), cyrillicChars.charAt(i));
        }

        return text;
    }

    @Override
    public Map<String, Vehicle> loadAll() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        int size = vehicles.size();
        Map<String, Vehicle> vehicleMap = new HashMap<>((int)(size/0.75+1));
        for (Vehicle v : vehicles) {
            vehicleMap.put(v.getVin(), v);
        }
        return vehicleMap;
    }
}
