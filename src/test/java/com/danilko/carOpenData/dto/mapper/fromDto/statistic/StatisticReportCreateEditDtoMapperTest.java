package com.danilko.carOpenData.dto.mapper.fromDto.statistic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.danilko.carOpenData.dto.createDto.statistic.StatisticReportCreateEditDto;
import com.danilko.carOpenData.entity.statistic.StatisticFilter;
import com.danilko.carOpenData.entity.statistic.StatisticReport;
import com.danilko.carOpenData.repository.StatisticFilterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class StatisticReportCreateEditDtoMapperTest {

    @Mock
    private StatisticFilterRepository statisticFilterRepository;

    @InjectMocks
    private StatisticReportCreateEditDtoMapper mapper;

    private StatisticReportCreateEditDto dto;
    private List<StatisticFilter> filters;

    @BeforeEach
    void setUp() {
        dto = new StatisticReportCreateEditDto();
        dto.setTitle("Test Title");
        dto.setDescription("Test Description");
        dto.setFilterIds(List.of(1L, 2L));

        var filter1 = new StatisticFilter();
        filter1.setId(1L);
        filter1.setName("Filter 1");
        var filter2 = new StatisticFilter();
        filter2.setId(2L);
        filter2.setName("Filter 2");
        filters = List.of(filter1, filter2);
    }

    @Test
    void shouldMapFromDtoToEntity() {
        when(statisticFilterRepository.findAllById(dto.getFilterIds())).thenReturn(filters);

        StatisticReport result = mapper.mapFrom(dto);

        assertNotNull(result);
        assertEquals(dto.getTitle(), result.getTitle());
        assertEquals(dto.getDescription(), result.getDescription());
        assertEquals(filters, result.getFilters());
    }
}
