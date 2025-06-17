package com.danilko.carOpenData.dto.mapper.fromDto.statistic;

import com.danilko.carOpenData.dto.createDto.statistic.StatisticFilterCreateEditDto;
import com.danilko.carOpenData.entity.Department;
import com.danilko.carOpenData.entity.FuelType;
import com.danilko.carOpenData.entity.Operation;
import com.danilko.carOpenData.entity.VehicleColor;
import com.danilko.carOpenData.entity.statistic.StatisticFilter;
import com.danilko.carOpenData.exception.ValidationException;
import com.danilko.carOpenData.repository.DepartmentRepository;
import com.danilko.carOpenData.repository.OperationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Year;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StatisticFilterCreateEditDtoMapperTest {

    @Mock
    private OperationRepository operationRepository;

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private StatisticFilterCreateEditDtoMapper mapper;

    @Test
    void mapFrom_ShouldMapAllFieldsCorrectly() {
        StatisticFilterCreateEditDto dto = new StatisticFilterCreateEditDto();
        dto.setName("Test Filter");
        dto.setBrand("Toyota");
        dto.setModel("Corolla");
        dto.setMakeYearFrom(2010);
        dto.setMakeYearTo(2020);
        dto.setColor("RED");
        dto.setKind("Sedan");
        dto.setBodyType("Hatchback");
        dto.setFuelType("PETROL");
        dto.setEngineCapacityFrom(1500);
        dto.setEngineCapacityTo(2000);

        dto.setOperationIds(List.of(1, 2));
        dto.setDepartmentIds(List.of(10, 20));

        Operation operation1 = new Operation(1, "Operation 1");
        Operation operation2 = new Operation(2, "Operation 2");
        Department department1 = new Department(10, "Department 1");
        Department department2 = new Department(20, "Department 2");

        Mockito.when(operationRepository.findAllById(List.of(1, 2))).thenReturn(List.of(operation1, operation2));
        Mockito.when(departmentRepository.findAllById(List.of(10, 20))).thenReturn(List.of(department1, department2));

        StatisticFilter result = mapper.mapFrom(dto);

        assertNotNull(result);
        assertEquals("Test Filter", result.getName());
        assertEquals("Toyota", result.getBrand());
        assertEquals("Corolla", result.getModel());
        assertEquals(2010, result.getMakeYearFrom());
        assertEquals(2020, result.getMakeYearTo());
        assertEquals(VehicleColor.RED, result.getColor());
        assertEquals("Sedan", result.getKind());
        assertEquals("Hatchback", result.getBodyType());
        assertEquals(FuelType.PETROL, result.getFuelType());
        assertEquals(1500, result.getEngineCapacityFrom());
        assertEquals(2000, result.getEngineCapacityTo());
        assertEquals(List.of(operation1, operation2), result.getOperations());
        assertEquals(List.of(department1, department2), result.getDepartments());
    }

    @Test
    void mapFrom_ShouldThrowValidationExceptionForInvalidColor() {
        // Arrange
        StatisticFilterCreateEditDto dto = new StatisticFilterCreateEditDto();
        dto.setColor("INVALID_COLOR");

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> mapper.mapFrom(dto));
        assertEquals("Color 'INVALID_COLOR' is not valid", exception.getMessage());
    }

    @Test
    void mapFrom_ShouldThrowValidationExceptionForInvalidFuelType() {
        // Arrange
        StatisticFilterCreateEditDto dto = new StatisticFilterCreateEditDto();
        dto.setFuelType("INVALID_FUEL");

        // Act & Assert
        ValidationException exception = assertThrows(ValidationException.class, () -> mapper.mapFrom(dto));
        assertEquals("FuelType 'INVALID_FUEL' is not valid", exception.getMessage());
    }

    @Test
    void mapFrom_ShouldHandleEmptyListsForOperationsAndDepartments() {
        // Arrange
        StatisticFilterCreateEditDto dto = new StatisticFilterCreateEditDto();

        // Act
        StatisticFilter result = mapper.mapFrom(dto);

        // Assert
        assertNotNull(result);
        assertTrue(result.getOperations().isEmpty());
        assertTrue(result.getDepartments().isEmpty());
    }

}