package com.danilko.carOpenData.controller;

import com.danilko.carOpenData.dto.utilDto.VehicleHistoryDto;
import com.danilko.carOpenData.service.VehicleHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class VehicleHistoryController {

    private final VehicleHistoryService vehicleHistoryService;


    @GetMapping("/vehicles/numplate/{numPlate}")
    ResponseEntity<VehicleHistoryDto> findByNumPlate(@PathVariable String numPlate){
        if (numPlate==null || numPlate.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        var result = vehicleHistoryService.findByNumberPlate(numPlate);

        return getVehicleHistoryDtoResponseEntity(result);
    }

    @GetMapping("/vehicles/vin/{vin}")
    ResponseEntity<VehicleHistoryDto> findByVin(@PathVariable String vin){

        if (vin==null || vin.isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        var result = vehicleHistoryService.findByVin(vin);

        return getVehicleHistoryDtoResponseEntity(result);
    }

    private ResponseEntity<VehicleHistoryDto> getVehicleHistoryDtoResponseEntity(VehicleHistoryDto result) {
        boolean isVehicleFound = result.getVehicleReadDto().isPresent();
        boolean isActionsFound = !result.getActionReadDtoList().isEmpty();
        boolean isWanted = result.getStealInfoDto().isPresent();

        if (!isVehicleFound && !isActionsFound && !isWanted){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
}




