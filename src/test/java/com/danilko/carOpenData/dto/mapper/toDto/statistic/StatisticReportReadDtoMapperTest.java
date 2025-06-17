package com.danilko.carOpenData.dto.mapper.toDto.statistic;

import com.danilko.carOpenData.dto.readDto.statistic.StatisticReportReadDto;
import com.danilko.carOpenData.entity.statistic.StatisticFilter;
import com.danilko.carOpenData.entity.statistic.StatisticReport;
import com.danilko.carOpenData.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class StatisticReportReadDtoMapperTest {

    @Mock
    private StatisticFilterReadDtoMapper statisticFilterReadDtoMapper;

    @InjectMocks
    private StatisticReportReadDtoMapper statisticReportReadDtoMapper;

    private StatisticReport statisticReport;

    @BeforeEach
    void setUp() {
        statisticReport = new StatisticReport();
        statisticReport.setId(1L);
        statisticReport.setTitle("Test Report");
        statisticReport.setDescription("Test Description");
        statisticReport.setFilters(Collections.emptyList());
    }

    @Test
    void shouldMapFromStatisticReportWithEmptyFilters() {
        StatisticReportReadDto result = statisticReportReadDtoMapper.mapFrom(statisticReport);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Report", result.getTitle());
        assertEquals("Test Description", result.getDescription());
        assertNotNull(result.getFilters());
        assertTrue(result.getFilters().isEmpty());
    }

    @Test
    void shouldMapFromStatisticReportWithFilters() {
        var filters = Arrays.asList(new StatisticFilter(), new StatisticFilter());
        statisticReport.setFilters(filters);
        StatisticReportReadDto result = statisticReportReadDtoMapper.mapFrom(statisticReport);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Report", result.getTitle());
        assertEquals("Test Description", result.getDescription());
        assertNotNull(result.getFilters());
        assertEquals(2, result.getFilters().size());
    }

    @Test
    void shouldThrowValidationExceptionWhenStatisticReportIsNull() {
        assertThrows(ValidationException.class, () -> statisticReportReadDtoMapper.mapFrom(null));
    }
}