package com.danilko.carOpenData.service.statistic;

import com.danilko.carOpenData.dto.mapper.toDto.FilterDtoMapper;
import com.danilko.carOpenData.dto.mapper.toDto.statistic.StatisticReadDtoMapper;
import com.danilko.carOpenData.dto.readDto.statistic.StatisticReadDto;
import com.danilko.carOpenData.dto.utilDto.ActionFilterDto;
import com.danilko.carOpenData.entity.Action;
import com.danilko.carOpenData.entity.statistic.Statistic;
import com.danilko.carOpenData.entity.statistic.StatisticFilter;
import com.danilko.carOpenData.exception.EntityNotFoundException;
import com.danilko.carOpenData.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class NewStatisticServiceImpl implements StatisticService {
    private final ActionRepository actionRepository;
    private final FilterDtoMapper filterDtoMapper;
    private final StatisticRepository statisticRepository;
    private final StatisticReadDtoMapper statisticReadDtoMapper;
    private final StatisticFilterRepository statisticFilterRepository;

    private static final Integer YEAR_WE_START_STATISTIC = 2020;
    private final StatisticReportRepository statisticReportRepository;

    @Override
    public boolean updateAllStatistic() {
        return false;
    }

    @Override
    public boolean createNewStatisticForFilter(Long filterId) {
        var filter = statisticFilterRepository.findById(filterId).orElseThrow(()-> new EntityNotFoundException("Filter with id " + filterId + " not found"));
        //Для кожного року беремо всі записи, потім фільтр за допомогою stream api.
        for (int i = YEAR_WE_START_STATISTIC; i <= Year.now().getValue(); i++) {
            statisticRepository.saveAll(getYearStatistic(Year.of(i), filter));
        }
        return true;

    }

    private List<Statistic> getYearStatistic(Year year, StatisticFilter filter) {
        var actionFilter = filterDtoMapper.mapFrom(filter);

        var statistic = new Statistic();
        var firstDayOfTheYear = year.atDay(1);
        var lastDayOfTheYear = year.atDay(year.length());

        actionFilter.setFromDate(Optional.of(firstDayOfTheYear));
        actionFilter.setToDate(Optional.of(lastDayOfTheYear));

        var yearStatistic = new Statistic();
        yearStatistic.setFilter(filter);
        yearStatistic.setFromDate(firstDayOfTheYear);
        yearStatistic.setToDate(lastDayOfTheYear);


        var listOfActions = actionRepository.findAll(new ActionSpecification(actionFilter));
        var resultList = new ArrayList<Statistic>((int)(listOfActions.size()/0.75+2));
        resultList.addAll(devideStatisticByMonths(listOfActions, year, filter));
        resultList.add(yearStatistic);
        return resultList;
    }

    private List<Statistic> devideStatisticByMonths(List<Action> listOfActions, Year currentYear, StatisticFilter filter) {
        var result = new ArrayList<Statistic>();
        var map = listOfActions.stream()
                .collect(Collectors.groupingBy(action -> action.getRegDate().getMonth()));
        for (int i = 1; i<=12; i++){
            var statistic = new Statistic();
            statistic.setFromDate(currentYear.atMonth(i).atDay(1));
            statistic.setToDate(currentYear.atMonth(i).atEndOfMonth());
            statistic.setFilter(filter);
            statistic.setCount(map.get(Month.of(i)).size());
            result.add(statistic);
        }
        return result;
    }

    @Override
    public List<StatisticReadDto> getStatisticOfYearsForReport(Long reportId) {
        return List.of();
    }

    @Override
    public List<StatisticReadDto> getStatisticOfYearForReport(Long reportId, Integer yearIso) {
        return List.of();
    }

    @Override
    public List<StatisticReadDto> getStatisticOfYearMonthForReport(Long reportId, Integer yearIso, Integer month) {
        return List.of();
    }

    @Override
    public List<StatisticReadDto> getStatisticOfYearsForFilter(Long filterId) {
        return List.of();
    }

    @Override
    public List<StatisticReadDto> getStatisticOfYearForFilter(Long filterId, Integer yearIso) {
        return List.of();
    }
}
