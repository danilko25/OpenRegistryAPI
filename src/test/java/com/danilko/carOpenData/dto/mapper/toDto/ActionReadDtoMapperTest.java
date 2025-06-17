package com.danilko.carOpenData.dto.mapper.toDto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.danilko.carOpenData.dto.readDto.ActionReadDto;
import com.danilko.carOpenData.dto.readDto.DepartmentReadDto;
import com.danilko.carOpenData.dto.readDto.OperationReadDto;
import com.danilko.carOpenData.dto.readDto.VehicleReadDto;
import com.danilko.carOpenData.entity.Action;
import com.danilko.carOpenData.entity.Department;
import com.danilko.carOpenData.entity.Operation;
import com.danilko.carOpenData.entity.Vehicle;
import com.danilko.carOpenData.exception.ValidationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class ActionReadDtoMapperTest {

    @Mock
    private OperationReadDtoMapper operationReadDtoMapper;

    @Mock
    private DepartmentReadDtoMapper departmentReadDtoMapper;

    @Mock
    private VehicleReadDtoMapper vehicleReadDtoMapper;

    @InjectMocks
    private ActionReadDtoMapper mapper;

    @Test
    void mapFrom_ShouldMapActionToDto() {
        Department department = new Department();
        Operation operation = new Operation();
        Vehicle vehicle = new Vehicle();

        Action action = new Action(5L, "P", "1412336900", LocalDate.of(2024, 6, 11),
                operation, department, vehicle, "КА9070ММ");

        OperationReadDto operationDto = new OperationReadDto();
        DepartmentReadDto departmentDto = new DepartmentReadDto();
        VehicleReadDto vehicleDto = new VehicleReadDto();

        when(operationReadDtoMapper.mapFrom(any(Operation.class))).thenReturn(operationDto);
        when(departmentReadDtoMapper.mapFrom(any(Department.class))).thenReturn(departmentDto);
        when(vehicleReadDtoMapper.mapFrom(any(Vehicle.class))).thenReturn(vehicleDto);

        ActionReadDto dto = mapper.mapFrom(action);

        assertNotNull(dto);
        assertEquals(5L, dto.getId());
        assertEquals("P", dto.getPerson());
        assertEquals("1412336900", dto.getRegAddr());
        assertEquals(LocalDate.of(2024, 6, 11), dto.getRegDate());
        assertEquals(operationDto, dto.getOperationReadDto());
        assertEquals(departmentDto, dto.getDepartmentReadDto());
        assertEquals(vehicleDto, dto.getVehicleReadDto());
        assertEquals("КА9070ММ", dto.getNumberPlate());
    }

    @Test
    void mapFrom_ShouldThrowException_WhenActionIsNull() {
        ValidationException exception = assertThrows(ValidationException.class, () -> mapper.mapFrom(null));
        assertEquals("Action is null", exception.getMessage());
    }
}
