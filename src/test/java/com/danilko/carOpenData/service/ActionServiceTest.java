package com.danilko.carOpenData.service;

import com.danilko.carOpenData.dto.utilDto.ActionFilterDto;
import com.danilko.carOpenData.dto.utilDto.VehicleFilterDto;
import com.danilko.carOpenData.repository.ActionSpecification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ActionServiceTest {

    @Autowired
    ActionService actionService;

    @Transactional
    @Test
    void countTest(){
        var filter3 = new ActionFilterDto();
//        filter3.setDepartmentCodes(List.of(12295));
//        System.out.println(actionService.countAll(fi lter3).size());
        System.out.println(actionService.countAll(new ActionFilterDto()).size());
    }


    @Transactional
    @Test
    void filterTest(){

        for (int i = 0; i<10; i++){
//            var filter1 = new ActionFilterDto();
//            filter1.setFromDate(Optional.of(LocalDate.of(2024, 9, 30)));
//            var vehicleFilter1 = new VehicleFilterDto();
//            vehicleFilter1.setVin(Optional.of("ZFA22300005115627"));
//            filter1.setVehicle(vehicleFilter1);
//            actionService.findAll(filter1);
//
//            var filter2 = new ActionFilterDto();
//            var vehicleFilter2 = new VehicleFilterDto();
//            vehicleFilter2.setVin(Optional.of("Z94CB41AADR138195"));
//            filter2.setVehicle(vehicleFilter2);
//            actionService.findAll(filter2);

            var filter3 = new ActionFilterDto();
            filter3.setDepartmentCodes(List.of(12295));
            actionService.findAll(filter3);
        }

    }
}
