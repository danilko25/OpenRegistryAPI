package com.danilko.carOpenData.controller;

import com.danilko.carOpenData.dto.createDto.statistic.StatisticReportCreateEditDto;
import com.danilko.carOpenData.dto.readDto.statistic.StatisticFilterReadDto;
import com.danilko.carOpenData.dto.readDto.statistic.StatisticReadDto;
import com.danilko.carOpenData.dto.readDto.statistic.StatisticReportReadDto;
import com.danilko.carOpenData.service.statistic.StatisticReportService;
import com.danilko.carOpenData.service.statistic.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
public class StatisticReportController {

    private final StatisticReportService statisticReportService;
    private final StatisticService statisticService;


    @GetMapping
    public List<StatisticReportReadDto> getAllReports() {
        return statisticReportService.findAll();
    }

    @PostMapping
    public StatisticReportReadDto addReport(@RequestBody StatisticReportCreateEditDto statisticDto) {
        return statisticReportService.save(statisticDto);
    }

    @GetMapping("/{reportId}")
    public StatisticReportReadDto findReportById(@PathVariable Long reportId) {
        return statisticReportService.findById(reportId);
    }

    @PutMapping("/{reportId}")
    public StatisticReportReadDto updateReport(@PathVariable Long reportId, @RequestBody StatisticReportCreateEditDto statisticDto) {
        return statisticReportService.edit(reportId, statisticDto);
    }

    @DeleteMapping("/{reportId}")
    public void deleteReport(@PathVariable Long reportId) {
        statisticReportService.delete(reportId);
    }

    @GetMapping("/{reportId}/filters")
    public List<StatisticFilterReadDto> getFiltersByReportId(@PathVariable Long reportId) {
        return statisticReportService.findById(reportId).getFilters();
    }

    @GetMapping("/{reportId}/statistic")
    public List<StatisticReadDto> getYearsStatisticForReport(@PathVariable Long reportId) {
        return statisticService.getStatisticOfYearsForReport(reportId);
    }

    @GetMapping("/{reportId}/statistic/{year}")
    public List<StatisticReadDto> getYearStatisticForReport(@PathVariable Long reportId,
                                                            @PathVariable Integer year) {
        return statisticService.getStatisticOfYearForReport(reportId, year);
    }

    @GetMapping("/{reportId}/statistic/{year}/{month}")
    public List<StatisticReadDto> getYearMonthStatisticForReport(@PathVariable Long reportId,
                                                                 @PathVariable Integer year,
                                                                 @PathVariable Integer month) {
        return statisticService.getStatisticOfYearMonthForReport(reportId, year, month);
    }
}
