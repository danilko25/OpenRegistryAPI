package com.danilko.carOpenData.service.statistic;

import com.danilko.carOpenData.dto.mapper.fromDto.statistic.StatisticReportCreateEditDtoMapper;
import com.danilko.carOpenData.dto.mapper.toDto.statistic.StatisticReportReadDtoMapper;
import com.danilko.carOpenData.dto.createDto.statistic.StatisticReportCreateEditDto;
import com.danilko.carOpenData.dto.readDto.statistic.StatisticReportReadDto;
import com.danilko.carOpenData.exception.EntityNotFoundException;
import com.danilko.carOpenData.repository.StatisticFilterRepository;
import com.danilko.carOpenData.repository.StatisticReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StatisticReportServiceImpl implements StatisticReportService {

    private final StatisticReportRepository statisticReportRepository;
    private final StatisticReportCreateEditDtoMapper statisticReportCreateEditDtoMapper;
    private final StatisticReportReadDtoMapper statisticReportReadDtoMapper;


    @Transactional
    @Override
    public StatisticReportReadDto save(StatisticReportCreateEditDto statisticReportCreateEditDto) {
        var statisticReport = statisticReportCreateEditDtoMapper.mapFrom(statisticReportCreateEditDto);

        var savedStatisticReport = statisticReportRepository.save(statisticReport);

        return statisticReportReadDtoMapper.mapFrom(savedStatisticReport);
    }

    @Transactional
    @Override
    public StatisticReportReadDto edit(Long id, StatisticReportCreateEditDto statisticReportCreateEditDto) {
        statisticReportRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("StatisticReport with id " + id + " not found"));

        var updatedStatisticReport = statisticReportCreateEditDtoMapper.mapFrom(statisticReportCreateEditDto);
        updatedStatisticReport.setId(id);
        var savedStatisticReport = statisticReportRepository.save(updatedStatisticReport);
        return statisticReportReadDtoMapper.mapFrom(savedStatisticReport);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        statisticReportRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("StatisticReport with id " + id + " not found"));
        statisticReportRepository.deleteById(id);
    }

    @Override
    public StatisticReportReadDto findById(Long id) {
        var statisticReport = statisticReportRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("StatisticReport with id " + id + " not found"));
        return statisticReportReadDtoMapper.mapFrom(statisticReport);
    }

    @Override
    public List<StatisticReportReadDto> findAll() {
        return statisticReportRepository.findAll().stream()
                .map(statisticReportReadDtoMapper::mapFrom)
                .toList();
    }
}
