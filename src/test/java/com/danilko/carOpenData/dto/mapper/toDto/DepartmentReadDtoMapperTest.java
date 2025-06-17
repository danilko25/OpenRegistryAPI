package com.danilko.carOpenData.dto.mapper.toDto;

import com.danilko.carOpenData.entity.Department;
import com.danilko.carOpenData.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class DepartmentReadDtoMapperTest {

    private DepartmentReadDtoMapper departmentReadDtoMapper;

    @BeforeEach
    void setUp() {
        departmentReadDtoMapper = new DepartmentReadDtoMapper();
    }


    @Test
    void shouldMapEntityToDto() {
        Department department = new Department();
        department.setId(12392);
        department.setName("ТСЦ 7345");

        var readDto = departmentReadDtoMapper.mapFrom(department);

        assertNotNull(readDto);
        assertEquals(12392, readDto.getId());
        assertEquals("ТСЦ 7345", readDto.getName());
    }

    @Test
    void shouldThrowValidationExceptionWhenEntityIsNull() {
        assertThrows(ValidationException.class, () -> departmentReadDtoMapper.mapFrom(null));
    }
}
