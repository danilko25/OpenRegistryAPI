package com.danilko.carOpenData.service.statistic;

import com.danilko.carOpenData.dto.createDto.statistic.StatisticReportCreateEditDto;
import com.danilko.carOpenData.dto.readDto.statistic.StatisticReportReadDto;

import java.util.List;

public interface StatisticReportService {

    StatisticReportReadDto save(StatisticReportCreateEditDto statisticReportCreateEditDto);

    StatisticReportReadDto edit(Long id, StatisticReportCreateEditDto statisticReportCreateEditDto);

    void delete(Long id);

    StatisticReportReadDto findById(Long id);

    List<StatisticReportReadDto> findAll();

}
