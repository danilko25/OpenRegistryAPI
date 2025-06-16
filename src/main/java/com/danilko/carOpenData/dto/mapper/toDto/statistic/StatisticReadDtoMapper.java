package com.danilko.carOpenData.dto.mapper.toDto.statistic;

import com.danilko.carOpenData.dto.mapper.Mapper;
import com.danilko.carOpenData.dto.readDto.statistic.StatisticReadDto;
import com.danilko.carOpenData.entity.statistic.Statistic;
import com.danilko.carOpenData.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class StatisticReadDtoMapper implements Mapper<Statistic, StatisticReadDto> {

    private final StatisticFilterReadDtoMapper statisticFilterReadDtoMapper;

    @Override
    public StatisticReadDto mapFrom(Statistic statistic) {
        if (statistic == null) {
            throw new ValidationException("Statistic is null");
        }
        StatisticReadDto statisticReadDto = new StatisticReadDto();

        statisticReadDto.setId(statistic.getId());
        statisticReadDto.setFilter(statisticFilterReadDtoMapper.mapFrom(statistic.getFilter()));
        statisticReadDto.setFromDate(statistic.getFromDate());
        statisticReadDto.setToDate(statistic.getToDate());
        statisticReadDto.setCount(statistic.getCount());
        return statisticReadDto;
    }
}
