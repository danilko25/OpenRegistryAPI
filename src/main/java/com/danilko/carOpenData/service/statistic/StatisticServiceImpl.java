package com.danilko.carOpenData.service.statistic;

import com.danilko.carOpenData.dto.mapper.toDto.FilterDtoMapper;
import com.danilko.carOpenData.dto.mapper.toDto.statistic.StatisticReadDtoMapper;
import com.danilko.carOpenData.dto.readDto.statistic.StatisticReadDto;
import com.danilko.carOpenData.entity.Action;
import com.danilko.carOpenData.entity.statistic.Statistic;
import com.danilko.carOpenData.entity.statistic.StatisticFilter;
import com.danilko.carOpenData.exception.EntityNotFoundException;
import com.danilko.carOpenData.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StatisticServiceImpl implements StatisticService {

    private final ActionRepository actionRepository;
    private final FilterDtoMapper filterDtoMapper;
    private final StatisticRepository statisticRepository;
    private final StatisticReadDtoMapper statisticReadDtoMapper;
    private final StatisticFilterRepository statisticFilterRepository;

    private static final Integer YEAR_WE_START_STATISTIC = 2021;
    private final StatisticReportRepository statisticReportRepository;


    @Transactional
    @Override
    public boolean updateAllStatistic() {
        var actualYearMonth = YearMonth.now();
        Year yearToUpdate;
        if (actualYearMonth.getMonthValue() == 1){
            yearToUpdate = Year.of(actualYearMonth.getYear()-1);
        }else yearToUpdate = Year.of(actualYearMonth.getYear());

        var filters = statisticFilterRepository.findAll();

        for (StatisticFilter filter : filters) {
            statisticRepository.deleteAllByFilterIdAndFromDateGreaterThanEqual(filter.getId(), yearToUpdate.atDay(1));
            statisticRepository.saveAll(getYearStatistic(yearToUpdate, filter));
        }
        return true;
    }

    @Override
    public boolean createNewStatisticForFilter(Long filterId) {
        var filter = statisticFilterRepository.findById(filterId).orElseThrow(()-> new EntityNotFoundException("Filter with id " + filterId + " not found"));
            for (int i = YEAR_WE_START_STATISTIC; i <= Year.now().getValue(); i++) {
            statisticRepository.saveAll(getYearStatistic(Year.of(i), filter));
        }
        return true;
    }

    private List<Statistic> getYearStatistic(Year year, StatisticFilter filter) {
        var actionFilter = filterDtoMapper.mapFrom(filter);
        var firstDayOfTheYear = year.atDay(1);
        var lastDayOfTheYear = year.atDay(year.length());
        actionFilter.setFromDate(Optional.of(firstDayOfTheYear));
        actionFilter.setToDate(Optional.of(lastDayOfTheYear));

        var listOfActions = actionRepository.findAll(new ActionSpecification(actionFilter));
        var yearStatistic = new Statistic();
        yearStatistic.setFilter(filter);
        yearStatistic.setFromDate(firstDayOfTheYear);
        yearStatistic.setToDate(lastDayOfTheYear);
        yearStatistic.setCount(listOfActions.size());

        var resultList = new ArrayList<Statistic>((int)(listOfActions.size()/0.75+2));
        resultList.addAll(divideStatisticByMonths(listOfActions, year, filter));
        resultList.add(yearStatistic);
        return resultList;
    }

    private List<Statistic> divideStatisticByMonths(List<Action> listOfActions, Year currentYear, StatisticFilter filter) {
        System.out.println("Start of dividing statistic");
        var result = new ArrayList<Statistic>();
        var map = listOfActions.stream()
                .collect(Collectors.groupingBy(action -> action.getRegDate().getMonth()));
        for (int i = 1; i<=12; i++){
            var statistic = new Statistic();
            statistic.setFromDate(currentYear.atMonth(i).atDay(1));
            statistic.setToDate(currentYear.atMonth(i).atEndOfMonth());
            statistic.setFilter(filter);
            var listOfActionByMonth = map.get(Month.of(i));
            if (listOfActionByMonth!=null){
                statistic.setCount(listOfActionByMonth.size());
            }else statistic.setCount(0);
            result.add(statistic);
        }
        return result;
    }


    @Override
    public List<StatisticReadDto> getStatisticOfYearsForReport(Long reportId) {
        var report = statisticReportRepository.findById(reportId).orElseThrow(()-> new EntityNotFoundException("Report with id " + reportId + " not found"));

        var result = new ArrayList<StatisticReadDto>();
        for (StatisticFilter filter : report.getFilters()){
            addAllYearStatisticByFilter(result, filter);
        }

        return result;
    }

    @Override
    public List<StatisticReadDto> getStatisticOfYearMonthForReport(Long reportId, Integer year, Integer month) {

        var result = new ArrayList<StatisticReadDto>();
        var report = statisticReportRepository.findById(reportId).orElseThrow(() -> new EntityNotFoundException("Report with id " + reportId + " not found"));
        YearMonth yearMonth;

        try{
            yearMonth = YearMonth.of(year, month);
        } catch (DateTimeException e){
            throw new RuntimeException("Wrong year or month entered");
        }

        for (StatisticFilter filter : report.getFilters()){
            var statisticList = statisticRepository.findAllByFilterIdAndFromDateEqualsAndToDateEquals(filter.getId(), yearMonth.atDay(1), yearMonth.atEndOfMonth());
            result.addAll(statisticList.stream().map(statisticReadDtoMapper::mapFrom).toList());
        }

        return result;
    }

    @Override
    public List<StatisticReadDto> getStatisticOfYearForReport(Long reportId, Integer yearIso) {

        var result = new ArrayList<StatisticReadDto>();
        var report = statisticReportRepository.findById(reportId).orElseThrow(() -> new EntityNotFoundException("Report with id " + reportId + " not found"));
        Year year;

        try {
            year = Year.of(yearIso);
        } catch (DateTimeException e) {
            throw new RuntimeException("Wrong format of year");
        }

        for(StatisticFilter filter : report.getFilters()){
            var statisticList = statisticRepository.findAllByFilterIdAndFromDateGreaterThanEqualAndToDateLessThanEqual(filter.getId(), year.atDay(1), year.atDay(year.length()));
            result.addAll(statisticList.stream().map(statisticReadDtoMapper::mapFrom).toList());
        }

        return result;
    }

    @Override
    public List<StatisticReadDto> getStatisticOfYearsForFilter(Long filterId) {
        var result = new ArrayList<StatisticReadDto>();
        var filter = statisticFilterRepository.findById(filterId).orElseThrow(() -> new EntityNotFoundException("Filter with id " + filterId + " not found"));

        addAllYearStatisticByFilter(result, filter);

        return result;
    }

    private void addAllYearStatisticByFilter(ArrayList<StatisticReadDto> result, StatisticFilter filter) {
        for (int i = YEAR_WE_START_STATISTIC; i< Year.now().getValue(); i++){
            Year year = Year.of(i);
            var statisticList = statisticRepository.findAllByFilterIdAndFromDateEqualsAndToDateEquals(filter.getId(), year.atDay(1), year.atDay(year.length()));
            result.addAll(statisticList.stream().map(statisticReadDtoMapper::mapFrom).toList());
        }
    }

    @Override
    public List<StatisticReadDto> getStatisticOfYearForFilter(Long filterId, Integer yearIso) {
        var filter = statisticFilterRepository.findById(filterId).orElseThrow(() -> new EntityNotFoundException("Filter with id " + filterId + " not found"));
        Year year;

        try {
            year = Year.of(yearIso);
        } catch (DateTimeException e) {
            throw new RuntimeException("Wrong format of year");
        }

        var statisticList = statisticRepository.findAllByFilterIdAndFromDateGreaterThanEqualAndToDateLessThanEqual(filter.getId(), year.atDay(1), year.atDay(year.length()));
        return statisticList.stream().map(statisticReadDtoMapper::mapFrom).toList();
    }
}
