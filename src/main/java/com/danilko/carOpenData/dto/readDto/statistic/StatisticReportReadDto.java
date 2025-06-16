package com.danilko.carOpenData.dto.readDto.statistic;

import lombok.Data;

import java.util.List;

@Data
public class StatisticReportReadDto {
    private Long id;
    private String title;
    private String description;
    private List<StatisticFilterReadDto> filters;
}
