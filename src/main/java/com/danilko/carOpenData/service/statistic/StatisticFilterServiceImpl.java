package com.danilko.carOpenData.service.statistic;

import com.danilko.carOpenData.dto.mapper.fromDto.statistic.StatisticFilterCreateEditDtoMapper;
import com.danilko.carOpenData.dto.mapper.toDto.statistic.StatisticFilterReadDtoMapper;
import com.danilko.carOpenData.dto.createDto.statistic.StatisticFilterCreateEditDto;
import com.danilko.carOpenData.dto.readDto.statistic.StatisticFilterReadDto;
import com.danilko.carOpenData.entity.statistic.StatisticFilter;
import com.danilko.carOpenData.exception.EntityNotFoundException;
import com.danilko.carOpenData.repository.StatisticFilterRepository;
import com.danilko.carOpenData.repository.StatisticRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class StatisticFilterServiceImpl implements StatisticFilterService {

    private final StatisticFilterRepository statisticFilterRepository;
    private final StatisticFilterCreateEditDtoMapper statisticFilterCreateEditDtoMapper;
    private final StatisticFilterReadDtoMapper statisticFilterReadDtoMapper;
    private final StatisticRepository statisticRepository;
    private final StatisticService statisticService;

    @Override
    public StatisticFilterReadDto save(StatisticFilterCreateEditDto filterCreateEditDto) {

        StatisticFilter filter = statisticFilterCreateEditDtoMapper.mapFrom(filterCreateEditDto);
        statisticFilterRepository.save(filter);
        statisticService.createNewStatisticForFilter(filter.getId());

        return statisticFilterReadDtoMapper.mapFrom(filter);
    }

    @Override
    public StatisticFilterReadDto edit(Long id, StatisticFilterCreateEditDto filterDto) {
        statisticFilterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Filter with id: " + id + " not found"));

        var updatedFilter = statisticFilterCreateEditDtoMapper.mapFrom(filterDto);
        updatedFilter.setId(id);
        statisticFilterRepository.save(updatedFilter);
        statisticRepository.deleteAllByFilterId(id);
        statisticService.createNewStatisticForFilter(id);

        return statisticFilterReadDtoMapper.mapFrom(updatedFilter);
    }

    @Transactional(readOnly = true)
    @Override
    public StatisticFilterReadDto findById(Long id) {
        return statisticFilterReadDtoMapper.mapFrom(
                statisticFilterRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Filter with id: " + id + " not found"))
        );
    }

    @Override
    public boolean delete(Long id) {

        statisticFilterRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Filter with id " + id + " not found"));
        statisticRepository.deleteAllByFilterId(id);
        statisticFilterRepository.deleteById(id);
        return true;
    }

    @Transactional(readOnly = true)
    @Override
    public List<StatisticFilterReadDto> findAll() {
        return statisticFilterRepository.findAll().stream()
                .map(statisticFilterReadDtoMapper::mapFrom)
                .toList();
    }
}
