package com.danilko.carOpenData.dto.mapper.toDto.statistic;

import com.danilko.carOpenData.dto.mapper.Mapper;
import com.danilko.carOpenData.dto.readDto.statistic.StatisticReportReadDto;
import com.danilko.carOpenData.entity.statistic.StatisticReport;
import com.danilko.carOpenData.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StatisticReportReadDtoMapper implements Mapper<StatisticReport, StatisticReportReadDto> {

    private final StatisticFilterReadDtoMapper statisticFilterReadDtoMapper;

    @Override
    public StatisticReportReadDto mapFrom(StatisticReport statisticReport) {
        if (statisticReport == null) {
            throw new ValidationException("StatisticReport is null");
        }
        StatisticReportReadDto statisticReportReadDto = new StatisticReportReadDto();
        statisticReportReadDto.setId(statisticReport.getId());
        statisticReportReadDto.setTitle(statisticReport.getTitle());
        statisticReportReadDto.setDescription(statisticReport.getDescription());
        statisticReportReadDto.setFilters(
                Optional.ofNullable(statisticReport.getFilters())
                        .orElse(Collections.emptyList())
                        .stream()
                        .map(statisticFilterReadDtoMapper::mapFrom)
                        .toList()
        );
        return statisticReportReadDto;
    }
}
