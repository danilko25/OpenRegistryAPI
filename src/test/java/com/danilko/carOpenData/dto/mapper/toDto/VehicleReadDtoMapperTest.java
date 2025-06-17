package com.danilko.carOpenData.dto.mapper.toDto;

import static org.junit.jupiter.api.Assertions.*;

import com.danilko.carOpenData.dto.readDto.VehicleReadDto;
import com.danilko.carOpenData.entity.Vehicle;
import com.danilko.carOpenData.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VehicleReadDtoMapperTest {

    private VehicleReadDtoMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new VehicleReadDtoMapper();
    }

    @Test
    void mapFrom_ShouldMapVehicleToDto() {
        Vehicle vehicle = new Vehicle("WF0LXXGBVLWP26427", "FORD", "TRANSIT", 1998, "БІЛИЙ",
                "ФУРГОН МАЛОТОНАЖНИЙ", "СПЕЦІАЛІЗОВАНИЙ", "ВАНТАЖНИЙ", "ДИЗЕЛЬНЕ ПАЛИВО", 2496, 1699.0, 2800.0);

        VehicleReadDto dto = mapper.mapFrom(vehicle);

        assertNotNull(dto);
        assertEquals("WF0LXXGBVLWP26427", dto.getVin());
        assertEquals("FORD", dto.getBrand());
        assertEquals("TRANSIT", dto.getModel());
        assertEquals(1998, dto.getMakeYear());
        assertEquals("БІЛИЙ", dto.getColor());
        assertEquals("ФУРГОН МАЛОТОНАЖНИЙ", dto.getKind());
        assertEquals("СПЕЦІАЛІЗОВАНИЙ", dto.getBodyType());
        assertEquals("ВАНТАЖНИЙ", dto.getPurpose());
        assertEquals("ДИЗЕЛЬНЕ ПАЛИВО", dto.getFuelType());
        assertEquals(2496, dto.getEngineCapacity());
        assertEquals(1699.0, dto.getOwnWeight());
        assertEquals(2800.0, dto.getTotalWeight());
    }

    @Test
    void mapFrom_ShouldThrowException_WhenVehicleIsNull() {
        ValidationException exception = assertThrows(ValidationException.class, () -> mapper.mapFrom(null));
        assertEquals("Vehicle is null", exception.getMessage());
    }
}

