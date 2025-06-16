package com.danilko.carOpenData.service.statistic;

import com.danilko.carOpenData.dto.createDto.statistic.StatisticFilterCreateEditDto;
import com.danilko.carOpenData.dto.readDto.statistic.StatisticFilterReadDto;
import java.util.List;


public interface StatisticFilterService {

    StatisticFilterReadDto save(StatisticFilterCreateEditDto filter);

    StatisticFilterReadDto edit(Long id, StatisticFilterCreateEditDto filter);

    StatisticFilterReadDto findById(Long id);

    List<StatisticFilterReadDto> findAll();

    boolean delete(Long id);
}
