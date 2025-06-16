package com.danilko.carOpenData.service.impl;

import com.danilko.carOpenData.dto.mapper.toDto.WantedVehicleReadDtoMapper;
import com.danilko.carOpenData.dto.readDto.WantedVehicleReadDto;
import com.danilko.carOpenData.entity.WantedVehicle;
import com.danilko.carOpenData.repository.WantedVehiclesRepository;
import com.danilko.carOpenData.service.WantedVehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WantedVehicleServiceImpl implements WantedVehicleService{
    private final WantedVehiclesRepository wantedVehiclesRepository;
    private final WantedVehicleReadDtoMapper wantedVehicleReadDtoMapper;

    @Transactional
    @Override
    public Optional<WantedVehicleReadDto> isWanted(String vin, String numberPlate) {
        Optional<WantedVehicle> wantedVehicleOptional = Optional.empty();

        if (vin!=null) {
            wantedVehicleOptional = wantedVehiclesRepository.findByVin(vin);
        }
        if (wantedVehicleOptional.isEmpty()) {
            wantedVehicleOptional = wantedVehiclesRepository.findByChassisNumber(vin);
            if (wantedVehicleOptional.isEmpty() && numberPlate!=null) {
                wantedVehicleOptional = wantedVehiclesRepository.findByNumberPlate(numberPlate);
            }
        }
        if (wantedVehicleOptional.isPresent()) {
            return wantedVehicleOptional.map(wantedVehicleReadDtoMapper::mapFrom);
        } else return Optional.empty();
    }
}
