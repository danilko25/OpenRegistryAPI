package com.danilko.carOpenData.dto.mapper.toDto;

import com.danilko.carOpenData.dto.mapper.Mapper;
import com.danilko.carOpenData.dto.utilDto.ActionFilterDto;
import com.danilko.carOpenData.dto.utilDto.VehicleFilterDto;
import com.danilko.carOpenData.entity.statistic.StatisticFilter;
import com.danilko.carOpenData.exception.ValidationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@Component
public class FilterDtoMapper implements Mapper<StatisticFilter, ActionFilterDto> {

    @Override
    public ActionFilterDto mapFrom(StatisticFilter filter) {
        if (filter == null) {
            throw new ValidationException("Filter is null");
        }
        var vehicle = new VehicleFilterDto();

        vehicle.setVin(getEmptyOptionalIfStringNullOrEmpty(filter.getVin()));
        vehicle.setBrand(getEmptyOptionalIfStringNullOrEmpty(filter.getBrand()));
        vehicle.setModel(getEmptyOptionalIfStringNullOrEmpty(filter.getModel()));
        vehicle.setMakeYearFrom(getEmptyOptionalIfIntNull(filter.getMakeYearFrom()));
        vehicle.setMakeYearTo(getEmptyOptionalIfIntNull(filter.getMakeYearTo()));


        if (filter.getColor() != null) {
            vehicle.setColor(Optional.of(filter.getColor().toUkrainian()));
        }else {
            vehicle.setColor(Optional.empty());
        }

        vehicle.setKind(getEmptyOptionalIfStringNullOrEmpty(filter.getKind()));
        vehicle.setBodyType(getEmptyOptionalIfStringNullOrEmpty(filter.getBodyType()));

        if (filter.getFuelType() != null) {
            vehicle.setFuelTypes(Collections.singletonList(filter.getFuelType().toUkrainian()));
        }else {
            vehicle.setFuelTypes(Collections.emptyList());
        }

        vehicle.setEngineCapacityFrom(Optional.ofNullable(filter.getEngineCapacityFrom()));
        vehicle.setEngineCapacityTo(Optional.ofNullable(filter.getEngineCapacityTo()));

        var departments = filter.getDepartments();
        var departmentCodes = new ArrayList<Integer>(departments.size());
        departments.forEach(department -> departmentCodes.add(department.getId()));

        var operations = filter.getOperations();
        var operationCodes = new ArrayList<Integer>(operations.size());
        operations.forEach(operation -> operationCodes.add(operation.getId()));

        var action = new ActionFilterDto();
        action.setVehicle(vehicle);
        action.setNumPlate(getEmptyOptionalIfStringNullOrEmpty(filter.getNumPlate()));
        action.setDepartmentCodes(departmentCodes);
        action.setOperationCodes(operationCodes);
        return action;
    }

    private Optional<String> getEmptyOptionalIfStringNullOrEmpty(String field){
        if (field == null || field.trim().isEmpty()){
            return Optional.empty();
        }
        return Optional.of(field.toUpperCase());
    }

    private Optional<Integer> getEmptyOptionalIfIntNull(Integer field){
        if (field == null || field == 0){
            return Optional.empty();
        }
        return Optional.of(field);
    }
}
