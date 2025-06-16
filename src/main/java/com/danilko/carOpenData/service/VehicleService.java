package com.danilko.carOpenData.service;

import com.danilko.carOpenData.dto.readDto.VehicleReadDto;
import com.danilko.carOpenData.dto.utilDto.ActionFilterDto;
import com.danilko.carOpenData.entity.Vehicle;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface VehicleService {

    Map<String, Vehicle> loadAll();

    Optional<VehicleReadDto> findByVin(String vin);
    List<VehicleReadDto> findAll(ActionFilterDto actionFilterDto);
}
