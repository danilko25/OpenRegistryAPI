package com.danilko.carOpenData.dto.createDto.statistic;

import lombok.Data;

import java.util.List;

@Data
public class StatisticFilterCreateEditDto {
    private String name;
    private String numPlate;
    private String vin;
    private String brand;
    private String model;
    private Integer makeYearFrom;
    private Integer makeYearTo;
    private String color;
    private String kind;
    private String bodyType;
    private String fuelType;
    private Integer engineCapacityFrom;
    private Integer engineCapacityTo;

    private List<Integer> operationIds;
    private List<Integer> departmentIds;
}
