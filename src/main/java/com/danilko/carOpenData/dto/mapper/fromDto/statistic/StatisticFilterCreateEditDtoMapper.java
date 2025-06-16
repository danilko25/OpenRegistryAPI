package com.danilko.carOpenData.dto.mapper.fromDto.statistic;

import com.danilko.carOpenData.dto.mapper.Mapper;
import com.danilko.carOpenData.dto.createDto.statistic.StatisticFilterCreateEditDto;
import com.danilko.carOpenData.entity.FuelType;
import com.danilko.carOpenData.entity.VehicleColor;
import com.danilko.carOpenData.entity.statistic.StatisticFilter;
import com.danilko.carOpenData.exception.ValidationException;
import com.danilko.carOpenData.repository.DepartmentRepository;
import com.danilko.carOpenData.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class StatisticFilterCreateEditDtoMapper implements Mapper<StatisticFilterCreateEditDto, StatisticFilter> {

    private final OperationRepository operationRepository;
    private final DepartmentRepository departmentRepository;


    @Override
    public StatisticFilter mapFrom(StatisticFilterCreateEditDto statisticFilterCreateEditDto) {

        if (statisticFilterCreateEditDto == null) {
            throw new ValidationException("StatisticFilterCreateEditDto is null");
        }
        StatisticFilter statisticFilter = new StatisticFilter();
        statisticFilter.setName(statisticFilterCreateEditDto.getName());
        statisticFilter.setNumPlate(returnNullIfStringIsEmpty(
                statisticFilterCreateEditDto.getNumPlate()));
        statisticFilter.setVin(returnNullIfStringIsEmpty(
                statisticFilterCreateEditDto.getVin()));
        statisticFilter.setBrand(returnNullIfStringIsEmpty(
                statisticFilterCreateEditDto.getBrand()));
        statisticFilter.setModel(returnNullIfStringIsEmpty(
                statisticFilterCreateEditDto.getModel()));
        statisticFilter.setMakeYearFrom(returnNullIfIntegerIsEmpty(
                statisticFilterCreateEditDto.getMakeYearFrom()));
        statisticFilter.setMakeYearTo(returnNullIfIntegerIsEmpty(
                statisticFilterCreateEditDto.getMakeYearTo()));


        if (statisticFilterCreateEditDto.getColor() == null || statisticFilterCreateEditDto.getColor().trim().isEmpty()) {
            statisticFilter.setColor(null);
        }else {
            try {
                statisticFilter.setColor(VehicleColor.fromValue(statisticFilterCreateEditDto.getColor().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new ValidationException("Color '" + statisticFilterCreateEditDto.getColor() + "' is not valid");
            }
        }

        if (statisticFilterCreateEditDto.getFuelType() == null || statisticFilterCreateEditDto.getFuelType().trim().isEmpty()) {
            statisticFilter.setFuelType(null);
        }else {
            try {
                statisticFilter.setFuelType(FuelType.valueOf(statisticFilterCreateEditDto.getFuelType()));
            } catch (IllegalArgumentException e) {
                throw new ValidationException("FuelType '" + statisticFilterCreateEditDto.getFuelType() + "' is not valid");
            }
        }

        statisticFilter.setKind(returnNullIfStringIsEmpty(
                statisticFilterCreateEditDto.getKind()));
        statisticFilter.setBodyType(returnNullIfStringIsEmpty(
                statisticFilterCreateEditDto.getBodyType()));
        statisticFilter.setEngineCapacityFrom(returnNullIfIntegerIsEmpty(
                statisticFilterCreateEditDto.getEngineCapacityFrom()));
        statisticFilter.setEngineCapacityTo(returnNullIfIntegerIsEmpty(
                statisticFilterCreateEditDto.getEngineCapacityTo()));

        // Handle relations
        if (statisticFilterCreateEditDto.getOperationIds() != null && !statisticFilterCreateEditDto.getOperationIds().isEmpty()) {
            statisticFilter.setOperations(operationRepository.findAllById(statisticFilterCreateEditDto.getOperationIds()));
        } else {
            statisticFilter.setOperations(Collections.emptyList());
        }

        if (statisticFilterCreateEditDto.getDepartmentIds() != null && !statisticFilterCreateEditDto.getDepartmentIds().isEmpty()) {
            statisticFilter.setDepartments(departmentRepository.findAllById(statisticFilterCreateEditDto.getDepartmentIds()));
        } else {
            statisticFilter.setDepartments(Collections.emptyList());
        }

        System.out.println(statisticFilter);

        return statisticFilter;
    }

    private String returnNullIfStringIsEmpty(String value){
        if (value == null || value.isEmpty()) {
            return null;
        }
        else return value.toUpperCase();
    }

    private Integer returnNullIfIntegerIsEmpty(Integer value){
        if (value == null || value <= 0) {
            return null;
        }
        else return value;
    }
}
