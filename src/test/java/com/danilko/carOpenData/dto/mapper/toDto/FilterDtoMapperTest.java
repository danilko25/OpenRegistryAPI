package com.danilko.carOpenData.dto.mapper.toDto;

import static org.junit.jupiter.api.Assertions.*;

import com.danilko.carOpenData.dto.utilDto.ActionFilterDto;
import com.danilko.carOpenData.entity.Department;
import com.danilko.carOpenData.entity.FuelType;
import com.danilko.carOpenData.entity.Operation;
import com.danilko.carOpenData.entity.VehicleColor;
import com.danilko.carOpenData.entity.statistic.StatisticFilter;
import com.danilko.carOpenData.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;

class FilterDtoMapperTest {

    private FilterDtoMapper filterDtoMapper;

    @BeforeEach
    void setUp() {
        filterDtoMapper = new FilterDtoMapper();
    }

    @Test
    void testMapFrom_ValidStatisticFilter() {
        // Arrange
        StatisticFilter statisticFilter = new StatisticFilter();
        statisticFilter.setVin("1HGCM82633A123456");
        statisticFilter.setBrand("Toyota");
        statisticFilter.setModel("Corolla");
        statisticFilter.setMakeYearFrom(2000);
        statisticFilter.setMakeYearTo(2020);
        statisticFilter.setColor(VehicleColor.RED);
        statisticFilter.setKind("ЛЕГКОВИЙ");
        statisticFilter.setBodyType("ХЕТЧБЕК");
        statisticFilter.setFuelType(FuelType.PETROL);
        statisticFilter.setEngineCapacityFrom(1500);
        statisticFilter.setEngineCapacityTo(2500);
        statisticFilter.setNumPlate("AA1111AA");

        Department department = new Department();
        department.setId(1);
        statisticFilter.setDepartments(Collections.singletonList(department));

        Operation operation = new Operation();
        operation.setId(2);
        statisticFilter.setOperations(Collections.singletonList(operation));

        // Act
        ActionFilterDto result = filterDtoMapper.mapFrom(statisticFilter);

        // Assert
        assertNotNull(result);
        assertEquals("1HGCM82633A123456", result.getVehicle().getVin().get());
        assertEquals("Toyota", result.getVehicle().getBrand().get());
        assertEquals("Corolla", result.getVehicle().getModel().get());
        assertEquals(2000, result.getVehicle().getMakeYearFrom().get());
        assertEquals(2020, result.getVehicle().getMakeYearTo().get());
        assertEquals(VehicleColor.RED.toUkrainian(), result.getVehicle().getColor().get());

        System.out.println(result.getVehicle().getColor().get());
        assertEquals("ЛЕГКОВИЙ", result.getVehicle().getKind().get());
        assertEquals("ХЕТЧБЕК", result.getVehicle().getBodyType().get());
        assertEquals(FuelType.PETROL.toString(), result.getVehicle().getFuelTypes().get(0));
        assertEquals(1500, result.getVehicle().getEngineCapacityFrom().get());
        assertEquals(2500, result.getVehicle().getEngineCapacityTo().get());
        assertEquals("AA1111AA", result.getNumPlate().get());
        assertEquals(1, result.getDepartmentCodes().size());
        assertEquals(1, result.getOperationCodes().size());
        assertEquals(1, result.getDepartmentCodes().get(0));
        assertEquals(2, result.getOperationCodes().get(0));
    }

    @Test
    void testMapFrom_FilterIsNull() {
        // Act & Assert
        assertThrows(ValidationException.class, () -> filterDtoMapper.mapFrom(null));
    }

    @Test
    void testMapFrom_EmptyFields() {
        // Arrange
        StatisticFilter statisticFilter = new StatisticFilter();
        statisticFilter.setVin(null);
        statisticFilter.setBrand(null);
        statisticFilter.setModel(null);
        statisticFilter.setMakeYearFrom(null);
        statisticFilter.setMakeYearTo(null);
        statisticFilter.setColor(null);
        statisticFilter.setKind(null);
        statisticFilter.setBodyType(null);
        statisticFilter.setFuelType(null);
        statisticFilter.setEngineCapacityFrom(null);
        statisticFilter.setEngineCapacityTo(null);
        statisticFilter.setNumPlate(null);

        statisticFilter.setDepartments(Collections.emptyList());
        statisticFilter.setOperations(Collections.emptyList());

        // Act
        ActionFilterDto result = filterDtoMapper.mapFrom(statisticFilter);

        // Assert
        assertNotNull(result);
        assertFalse(result.getVehicle().getVin().isPresent());
        assertFalse(result.getVehicle().getBrand().isPresent());
        assertFalse(result.getVehicle().getModel().isPresent());
        assertFalse(result.getVehicle().getMakeYearFrom().isPresent());
        assertFalse(result.getVehicle().getMakeYearTo().isPresent());
        assertFalse(result.getVehicle().getColor().isPresent());
        assertFalse(result.getVehicle().getKind().isPresent());
        assertFalse(result.getVehicle().getBodyType().isPresent());
        assertTrue(result.getVehicle().getFuelTypes().isEmpty());
        assertFalse(result.getVehicle().getEngineCapacityFrom().isPresent());
        assertFalse(result.getVehicle().getEngineCapacityTo().isPresent());
        assertFalse(result.getNumPlate().isPresent());
        assertTrue(result.getDepartmentCodes().isEmpty());
        assertTrue(result.getOperationCodes().isEmpty());
    }
}

