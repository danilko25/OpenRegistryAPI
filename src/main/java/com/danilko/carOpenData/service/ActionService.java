package com.danilko.carOpenData.service;

import com.danilko.carOpenData.dto.readDto.ActionReadDto;
import com.danilko.carOpenData.dto.readDto.VehicleReadDto;
import com.danilko.carOpenData.dto.utilDto.ActionFilterDto;


import java.util.List;
import java.util.Optional;


public interface ActionService {

    //Find only when plate.equals(numberPlate)
    Optional<ActionReadDto> findByNumberPlate(String numberPlate);

    //Find only when vin.equals(vin)
    List<ActionReadDto> findAllByVehicleVin(String vin);

    //Use vin.like(%vin%) and plate.like(%numberPlate%)
    List<ActionReadDto> findAll(ActionFilterDto actionFilterDto);

    List<ActionReadDto> findAllByStatisticFilter(Long statisticFilterId);

    List<VehicleReadDto> countAll(ActionFilterDto actionFilterDto);

    void deleteAllByRegDateYear(Integer regDateYear);
}
