package com.danilko.carOpenData.dto.readDto.statistic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticReadDto {
    private Long id;
    private StatisticFilterReadDto filter;
    private LocalDate fromDate;
    private LocalDate toDate;
    private int count;
}
