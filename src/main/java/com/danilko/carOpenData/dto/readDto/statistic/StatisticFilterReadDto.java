package com.danilko.carOpenData.dto.readDto.statistic;

import com.danilko.carOpenData.dto.readDto.DepartmentReadDto;
import com.danilko.carOpenData.dto.readDto.OperationReadDto;
import com.danilko.carOpenData.entity.FuelType;
import com.danilko.carOpenData.entity.VehicleColor;
import lombok.Data;

import java.util.List;

@Data
public class StatisticFilterReadDto {
    private Long id;
    private String name;
    private List<OperationReadDto> operations;
    private List<DepartmentReadDto> departments;

    private String numPlate;
    private String vin;
    private String brand;
    private String model;
    private Integer makeYearFrom;
    private Integer makeYearTo;
    private VehicleColor color;
    private String kind;
    private String bodyType;
    private FuelType fuelType;

    private Integer engineCapacityFrom;
    private Integer engineCapacityTo;
}
