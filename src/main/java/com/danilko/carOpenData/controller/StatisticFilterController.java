package com.danilko.carOpenData.controller;

import com.danilko.carOpenData.dto.createDto.statistic.StatisticFilterCreateEditDto;
import com.danilko.carOpenData.dto.createDto.statistic.StatisticReportCreateEditDto;
import com.danilko.carOpenData.dto.readDto.statistic.StatisticFilterReadDto;
import com.danilko.carOpenData.dto.readDto.statistic.StatisticReadDto;
import com.danilko.carOpenData.service.statistic.StatisticFilterService;
import com.danilko.carOpenData.service.statistic.StatisticReportService;
import com.danilko.carOpenData.service.statistic.StatisticService;
import com.danilko.carOpenData.service.statistic.StatisticServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/filters")
@RequiredArgsConstructor
public class StatisticFilterController {

    private final StatisticFilterService statisticFilterService;
    private final StatisticService statisticService;

    @GetMapping
    public List<StatisticFilterReadDto> getAllFilters() {
        return statisticFilterService.findAll();
    }

    @PostMapping
    public StatisticFilterReadDto createFilter(@RequestBody StatisticFilterCreateEditDto statisticFilterDto) {
        System.out.println(statisticFilterDto);
        return statisticFilterService.save(statisticFilterDto);
    }

    @GetMapping("/{filterId}")
    public StatisticFilterReadDto getFilterById(@PathVariable Long filterId) {
        return statisticFilterService.findById(filterId);
    }

    @PutMapping("/{filterId}")
    public StatisticFilterReadDto editFilter(@PathVariable Long filterId, @RequestBody StatisticFilterCreateEditDto statisticFilterDto){
        return statisticFilterService.edit(filterId, statisticFilterDto);
    }

    @DeleteMapping("/{filterId}")
    public void deleteFilter(@PathVariable Long filterId) {
        statisticFilterService.delete(filterId);
    }
    
    @GetMapping("/{filterId}/statistic")
    public List<StatisticReadDto> getAllYearsStatisticByFilterId(@PathVariable Long filterId) {
        return statisticService.getStatisticOfYearsForFilter(filterId);
    }

    @GetMapping("{filterId}/statistic/{year}")
    public List<StatisticReadDto> getYearStatisticByFilterId(@PathVariable Long filterId, @PathVariable Integer year) {
        return statisticService.getStatisticOfYearForFilter(filterId, year);
    }

}
