package com.danilko.carOpenData.service;

import com.danilko.carOpenData.dto.readDto.WantedVehicleReadDto;
import com.danilko.carOpenData.entity.WantedVehicle;

import java.util.Optional;

public interface WantedVehicleService {
    Optional<WantedVehicleReadDto> isWanted(String vin, String numberPlate);
}
