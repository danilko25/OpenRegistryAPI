package com.danilko.carOpenData.dto.readDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActionReadDto {
    private Long id;
    private String person;
    private String regAddr;
    private LocalDate regDate;
    private OperationReadDto operationReadDto;
    private DepartmentReadDto departmentReadDto;
//    private VehicleReadDto vehicleReadDto;
    private String vehicleVin;
    private String numberPlate;
}
