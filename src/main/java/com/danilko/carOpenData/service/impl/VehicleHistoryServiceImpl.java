package com.danilko.carOpenData.service.impl;

import com.danilko.carOpenData.dto.utilDto.VehicleHistoryDto;
import com.danilko.carOpenData.service.ActionService;
import com.danilko.carOpenData.service.VehicleHistoryService;
import com.danilko.carOpenData.service.VehicleService;
import com.danilko.carOpenData.service.WantedVehicleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class VehicleHistoryServiceImpl implements VehicleHistoryService {

    private final ActionService actionService;
    private final WantedVehicleService wantedVehicleService;
    private final VehicleService vehicleService;

//    @Override
//    public VehicleHistoryDto findByNumberPlate(String numberPlate) {
//        var actionOpt = actionService.findByNumberPlate(numberPlate);
//        if (actionOpt.isPresent()){
//            var actionReadDto = actionOpt.get();
//            var vehicleVin = actionReadDto.getVehicleReadDto().getVin();
//            var stealInfo = wantedVehicleService.isWanted(vehicleVin, numberPlate);
//            return new VehicleHistoryDto(Optional.of(actionReadDto.getVehicleReadDto()), stealInfo, actionService.findAllByVehicleVin(actionReadDto.getVehicleReadDto().getVin()));
//        }else return new VehicleHistoryDto(Optional.empty(),wantedVehicleService.isWanted(null, numberPlate), Collections.emptyList());
//    }

    @Override
    public VehicleHistoryDto findByNumberPlate(String numberPlate) {
        var actionOpt = actionService.findByNumberPlate(numberPlate);
        if (actionOpt.isPresent()){
            var actionReadDto = actionOpt.get();
            var vehicleOpt = vehicleService.findByVin(actionReadDto.getVehicleVin());
            var stealInfo = wantedVehicleService.isWanted(actionReadDto.getVehicleVin(), numberPlate);
            return new VehicleHistoryDto(vehicleOpt, stealInfo, actionService.findAllByVehicleVin(actionReadDto.getVehicleVin()));
        }else return new VehicleHistoryDto(Optional.empty(),wantedVehicleService.isWanted(null, numberPlate), Collections.emptyList());
    }

    @Override
    public VehicleHistoryDto findByVin(String vin) {
        var vehicleOpt = vehicleService.findByVin(vin);
        if (vehicleOpt.isPresent()){
            var actions = actionService.findAllByVehicleVin(vin);
            var actualNumberPlate = actions.get(0).getNumberPlate();
            var stealInfo = wantedVehicleService.isWanted(vin, actualNumberPlate);
            return new VehicleHistoryDto(vehicleOpt,stealInfo, actions);
        }
        return new VehicleHistoryDto(Optional.empty(),wantedVehicleService.isWanted(vin, null),Collections.emptyList());
    }
}
