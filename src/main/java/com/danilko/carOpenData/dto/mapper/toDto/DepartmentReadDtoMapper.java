package com.danilko.carOpenData.dto.mapper.toDto;

import com.danilko.carOpenData.dto.mapper.Mapper;
import com.danilko.carOpenData.dto.readDto.DepartmentReadDto;
import com.danilko.carOpenData.entity.Department;
import com.danilko.carOpenData.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class DepartmentReadDtoMapper implements Mapper<Department, DepartmentReadDto> {
    @Override
    public DepartmentReadDto mapFrom(Department department) {
        if(department == null){
            throw new ValidationException("Department is null");
        }
        return new DepartmentReadDto(department.getId(), department.getName());
    }
}
