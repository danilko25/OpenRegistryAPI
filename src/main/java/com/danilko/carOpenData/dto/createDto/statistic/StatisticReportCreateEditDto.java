package com.danilko.carOpenData.dto.createDto.statistic;

import lombok.Data;

import java.util.List;

@Data
public class StatisticReportCreateEditDto {
    private String title;
    private String description;
    private List<Long> filterIds;
}
