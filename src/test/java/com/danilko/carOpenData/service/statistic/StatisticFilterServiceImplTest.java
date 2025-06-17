package com.danilko.carOpenData.service.statistic;

import com.danilko.carOpenData.dto.createDto.statistic.StatisticFilterCreateEditDto;
import com.danilko.carOpenData.repository.StatisticFilterRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StatisticFilterServiceImplTest {

    @Autowired
    StatisticFilterRepository statisticFilterRepository;
    @Autowired
    StatisticFilterService statisticFilterService;


    @Test
    void save() {
        var statisticCountBeforeSaving = statisticFilterRepository.findAll().size();
        var filter = new StatisticFilterCreateEditDto();
        filter.setBrand("BMW");
        filter.setName("Test Filter");
        statisticFilterService.save(filter);
        var statisticCountAfterSaving = statisticFilterRepository.findAll().size();
        assertEquals(1, statisticCountAfterSaving-statisticCountBeforeSaving);

    }

    @Test
    void edit() {
        System.out.println("Helli");
        System.out.println(statisticFilterRepository.findAll().size());
    }

    @Test
    void findById() {
    }

    @Test
    void delete() {
    }

    @Test
    void findAll() {
    }
}