package com.danilko.carOpenData.service.statistic;

import com.danilko.carOpenData.dto.readDto.statistic.StatisticReadDto;

import java.util.List;

public interface StatisticService {

//    List<StatisticReadDto> getStatisticForFilterInPeriod(Long filterId, LocalDate startDate, LocalDate endDate);

    boolean updateAllStatistic();

    boolean createNewStatisticForFilter(Long filterId);

    List<StatisticReadDto> getStatisticOfYearsForReport(Long reportId);

    List<StatisticReadDto> getStatisticOfYearForReport(Long reportId, Integer yearIso);

    List<StatisticReadDto> getStatisticOfYearMonthForReport(Long reportId, Integer yearIso, Integer month);

    List<StatisticReadDto> getStatisticOfYearsForFilter(Long filterId);

    List<StatisticReadDto> getStatisticOfYearForFilter(Long filterId, Integer yearIso);

}
