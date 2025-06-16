package com.danilko.carOpenData.dto.mapper.toDto;

import com.danilko.carOpenData.dto.mapper.Mapper;
import com.danilko.carOpenData.dto.readDto.OperationReadDto;
import com.danilko.carOpenData.entity.Operation;
import com.danilko.carOpenData.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class OperationReadDtoMapper implements Mapper<Operation, OperationReadDto> {
    @Override
    public OperationReadDto mapFrom(Operation operation) {
        if (operation == null) {
            throw new ValidationException("Operation is null");
        }
        return new OperationReadDto(operation.getId(), operation.getName());
    }
}
