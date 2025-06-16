package com.danilko.carOpenData.dto.mapper.fromDto.statistic;

import com.danilko.carOpenData.dto.mapper.Mapper;
import com.danilko.carOpenData.dto.mapper.toDto.statistic.StatisticFilterReadDtoMapper;
import com.danilko.carOpenData.dto.createDto.statistic.StatisticReportCreateEditDto;
import com.danilko.carOpenData.entity.statistic.StatisticReport;
import com.danilko.carOpenData.exception.ValidationException;
import com.danilko.carOpenData.repository.StatisticFilterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class StatisticReportCreateEditDtoMapper implements Mapper<StatisticReportCreateEditDto, StatisticReport> {

    private final StatisticFilterRepository statisticFilterRepository;

    @Override
    public StatisticReport mapFrom(StatisticReportCreateEditDto statisticReportCreateEditDto) {

        if (statisticReportCreateEditDto == null) {
            throw new ValidationException("StatisticReportCreateEditDto is null");
        }
        StatisticReport statisticReport = new StatisticReport();
        statisticReport.setTitle(statisticReportCreateEditDto.getTitle());
        statisticReport.setDescription(statisticReportCreateEditDto.getDescription());
        statisticReport.setFilters(
                statisticFilterRepository.findAllById(statisticReportCreateEditDto.getFilterIds())
        );
        return statisticReport;
    }
}
