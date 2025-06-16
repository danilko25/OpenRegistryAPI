package com.danilko.carOpenData.dto.utilDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springdoc.core.annotations.ParameterObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@ToString
@ParameterObject
public class ActionFilterDto {
    Optional<LocalDate> fromDate;
    Optional<LocalDate> toDate;
    List<Integer> operationCodes;
    List<Integer> departmentCodes;
    VehicleFilterDto vehicle;
    Optional<String> numPlate;

    public ActionFilterDto(){
        fromDate = Optional.empty();
        toDate = Optional.empty();
        operationCodes = new ArrayList<>();
        departmentCodes = new ArrayList<>();
        vehicle = new VehicleFilterDto();
        numPlate = Optional.empty();
    }
}
