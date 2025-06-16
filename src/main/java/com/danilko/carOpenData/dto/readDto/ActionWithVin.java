package com.danilko.carOpenData.dto.readDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActionWithVin {
    private Long id;
    private String person;
    private String regAddr;
    private LocalDate regDate;
    private OperationReadDto operationReadDto;
    private DepartmentReadDto departmentReadDto;
    private String vehicleVin;
    private String numberPlate;
}
