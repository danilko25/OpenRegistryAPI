package com.danilko.carOpenData.repository;

import com.danilko.carOpenData.dto.utilDto.ActionFilterDto;
import com.danilko.carOpenData.entity.Action;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class ActionRepositoryTest {

    @Autowired
    ActionRepository actionRepository;

    @Transactional
    @Test
    void findAllByVinOrderTest(){
        var vin = "3FA6P0LU1HR234539";
        var res = actionRepository.findAllByVehicleVin(vin);
        for (Action action : res){
            System.out.println(action);
        }
        var last = actionRepository.findByVehicleVin(vin);
        System.out.println(last);
        last.ifPresent(action -> Assertions.assertEquals(action, res.get(0)));

        var query = actionRepository.findByVehicleVin(vin);
        System.out.println(query);
        Assertions.assertEquals(last.get(), query.get());
    }

    @Test
    void filterTest(){
        var filter3 = new ActionFilterDto();
        filter3.setDepartmentCodes(List.of(12295));

        System.out.println("--------------------------------------------------------------------------");
        System.out.println("FindAll(new ActionSpecification(filter3.setDepartmentCodes(List.of(12284)))");
        var startMemory = (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1_024_000;
        var start = System.currentTimeMillis();
        actionRepository.findAll(new ActionSpecification(filter3));
        var end = System.currentTimeMillis();
        var endMemory = (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1_024_000;

        System.out.println("Time in seconds: " + (end-start)/1000);
        System.out.println("Memory in mb: " + (endMemory-startMemory)/1000);


    }
}
