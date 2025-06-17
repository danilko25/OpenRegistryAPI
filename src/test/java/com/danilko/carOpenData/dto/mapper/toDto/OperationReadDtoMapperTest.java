package com.danilko.carOpenData.dto.mapper.toDto;

import com.danilko.carOpenData.entity.Operation;
import com.danilko.carOpenData.exception.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperationReadDtoMapperTest {

    private OperationReadDtoMapper operationReadDtoMapper;

    @BeforeEach
    void setUp() {
        operationReadDtoMapper = new OperationReadDtoMapper();
    }

    @Test
    void shouldMapEntityToDto() {
        Operation operation = new Operation();
        operation.setId(310);
        operation.setName("ПЕРЕРЕЄСТРАЦІЯ НА НОВОГО ВЛАСНИКА ЗА ДОГ. КУП.-ПРОД.ТА ІНШ.(ОФОРМ.НОТАРІАЛЬНО )");

        var readDto = operationReadDtoMapper.mapFrom(operation);

        assertNotNull(readDto);
        assertEquals(310, readDto.getId());
        assertEquals("ПЕРЕРЕЄСТРАЦІЯ НА НОВОГО ВЛАСНИКА ЗА ДОГ. КУП.-ПРОД.ТА ІНШ.(ОФОРМ.НОТАРІАЛЬНО )", readDto.getName());
    }

    @Test
    void shouldThrowValidationExceptionWhenEntityIsNull() {
        assertThrows(ValidationException.class, () -> operationReadDtoMapper.mapFrom(null));
    }
}