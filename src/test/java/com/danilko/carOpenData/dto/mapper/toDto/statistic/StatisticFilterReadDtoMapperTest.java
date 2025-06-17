package com.danilko.carOpenData.dto.mapper.toDto.statistic;

import static org.junit.jupiter.api.Assertions.*;

import com.danilko.carOpenData.dto.mapper.toDto.DepartmentReadDtoMapper;
import com.danilko.carOpenData.dto.mapper.toDto.OperationReadDtoMapper;
import com.danilko.carOpenData.dto.readDto.statistic.StatisticFilterReadDto;
import com.danilko.carOpenData.entity.Department;
import com.danilko.carOpenData.entity.Operation;
import com.danilko.carOpenData.entity.statistic.StatisticFilter;
import com.danilko.carOpenData.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
class StatisticFilterReadDtoMapperTest {

    @Mock
    private OperationReadDtoMapper operationReadDtoMapper;

    @Mock
    private DepartmentReadDtoMapper departmentReadDtoMapper;

    @InjectMocks
    private StatisticFilterReadDtoMapper statisticFilterReadDtoMapper;

    private StatisticFilter statisticFilter;

    @BeforeEach
    void setUp() {
        statisticFilter = new StatisticFilter();
        statisticFilter.setId(1L);
        statisticFilter.setName("Test Filter");
        statisticFilter.setOperations(Collections.emptyList());
        statisticFilter.setDepartments(Collections.emptyList());
        statisticFilter.setNumPlate("КЕ5431АЕ");
        statisticFilter.setVin("VF72RHNVWJ4259929");
        statisticFilter.setBrand("Toyota");
        statisticFilter.setModel("Corolla");
        statisticFilter.setMakeYearFrom(2010);
        statisticFilter.setMakeYearTo(2020);
        statisticFilter.setColor(null);
        statisticFilter.setKind("ЗАГАЛЬНИЙ");
        statisticFilter.setBodyType("ЛЕГКОВИЙ");
        statisticFilter.setFuelType(null);
        statisticFilter.setEngineCapacityFrom(1500);
        statisticFilter.setEngineCapacityTo(2000);
    }

    @Test
    void shouldMapFromStatisticFilter() {
        StatisticFilterReadDto result = statisticFilterReadDtoMapper.mapFrom(statisticFilter);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Filter", result.getName());
        assertNotNull(result.getOperations());
        assertTrue(result.getOperations().isEmpty());
        assertNotNull(result.getDepartments());
        assertTrue(result.getDepartments().isEmpty());
        assertEquals("КЕ5431АЕ", result.getNumPlate());
        assertEquals("VF72RHNVWJ4259929", result.getVin());
        assertEquals("Toyota", result.getBrand());
        assertEquals("Corolla", result.getModel());
        assertEquals(2010, result.getMakeYearFrom());
        assertEquals(2020, result.getMakeYearTo());
        assertEquals("ЗАГАЛЬНИЙ", result.getKind());
        assertEquals("ЛЕГКОВИЙ", result.getBodyType());
        assertEquals(1500, result.getEngineCapacityFrom());
        assertEquals(2000, result.getEngineCapacityTo());
    }

    @Test
    void shouldThrowValidationExceptionWhenStatisticFilterIsNull() {
        assertThrows(ValidationException.class, () -> statisticFilterReadDtoMapper.mapFrom(null));
    }

    @Test
    void shouldMapFromStatisticFilterWithOperationsAndDepartments() {
        var operations = Arrays.asList(new Operation(), new Operation());
        var departments = Arrays.asList(new Department(), new Department());

        statisticFilter.setOperations(operations);
        statisticFilter.setDepartments(departments);

        StatisticFilterReadDto result = statisticFilterReadDtoMapper.mapFrom(statisticFilter);

        assertNotNull(result);
        assertEquals(2, result.getOperations().size());
        assertEquals(2, result.getDepartments().size());
    }
}

