package com.danilko.carOpenData.dto.mapper.toDto.statistic;

import com.danilko.carOpenData.dto.mapper.Mapper;
import com.danilko.carOpenData.dto.mapper.toDto.DepartmentReadDtoMapper;
import com.danilko.carOpenData.dto.mapper.toDto.OperationReadDtoMapper;
import com.danilko.carOpenData.dto.readDto.DepartmentReadDto;
import com.danilko.carOpenData.dto.readDto.OperationReadDto;
import com.danilko.carOpenData.dto.readDto.statistic.StatisticFilterReadDto;
import com.danilko.carOpenData.entity.Department;
import com.danilko.carOpenData.entity.Operation;
import com.danilko.carOpenData.entity.statistic.StatisticFilter;
import com.danilko.carOpenData.exception.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StatisticFilterReadDtoMapper implements Mapper<StatisticFilter, StatisticFilterReadDto> {

    private final OperationReadDtoMapper operationReadDtoMapper;
    private final DepartmentReadDtoMapper departmentReadDtoMapper;

    @Override
    public StatisticFilterReadDto mapFrom(StatisticFilter statisticFilter) {
        if (statisticFilter == null) {
            throw new ValidationException("StatisticFilter is null");
        }
        StatisticFilterReadDto filterReadDto = new StatisticFilterReadDto();
        filterReadDto.setId(statisticFilter.getId());
        filterReadDto.setName(statisticFilter.getName());

        filterReadDto.setOperations(mapOperations(statisticFilter.getOperations()));
        filterReadDto.setDepartments(mapDepartments(statisticFilter.getDepartments()));

        filterReadDto.setNumPlate(statisticFilter.getNumPlate());
        filterReadDto.setVin(statisticFilter.getVin());
        filterReadDto.setBrand(statisticFilter.getBrand());
        filterReadDto.setModel(statisticFilter.getModel());
        filterReadDto.setMakeYearFrom(statisticFilter.getMakeYearFrom());
        filterReadDto.setMakeYearTo(statisticFilter.getMakeYearTo());
        filterReadDto.setColor(statisticFilter.getColor());
        filterReadDto.setKind(statisticFilter.getKind());
        filterReadDto.setBodyType(statisticFilter.getBodyType());
        filterReadDto.setFuelType(statisticFilter.getFuelType());
        filterReadDto.setEngineCapacityFrom(statisticFilter.getEngineCapacityFrom());
        filterReadDto.setEngineCapacityTo(statisticFilter.getEngineCapacityTo());

        return filterReadDto;
    }

    private List<OperationReadDto> mapOperations(List<Operation> operations) {
        return Optional.ofNullable(operations)
                .orElse(Collections.emptyList())
                .stream()
                .map(operationReadDtoMapper::mapFrom)
                .toList();
    }

    private List<DepartmentReadDto> mapDepartments(List<Department> departments) {
        return Optional.ofNullable(departments)
                .orElse(Collections.emptyList())
                .stream()
                .map(departmentReadDtoMapper::mapFrom)
                .toList();
    }
}
