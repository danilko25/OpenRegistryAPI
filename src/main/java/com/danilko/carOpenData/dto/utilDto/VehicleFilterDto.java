package com.danilko.carOpenData.dto.utilDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springdoc.core.annotations.ParameterObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@AllArgsConstructor
@ToString
@ParameterObject
public class VehicleFilterDto{
    private Optional<String> vin;
    private Optional<String> brand;
    private Optional<String> model;
    private Optional<Integer> makeYearFrom;
    private Optional<Integer> makeYearTo;
    private Optional<String> color;
    private Optional<String> kind;
    private Optional<String> bodyType;
    private List<String> fuelTypes;
    private Optional<Integer> engineCapacityFrom;
    private Optional<Integer> engineCapacityTo;

    public VehicleFilterDto(){
        vin = Optional.empty();
        brand = Optional.empty();
        model = Optional.empty();
        makeYearFrom = Optional.empty();
        makeYearTo = Optional.empty();
        color = Optional.empty();
        kind = Optional.empty();
        bodyType = Optional.empty();
        engineCapacityFrom = Optional.empty();
        engineCapacityTo = Optional.empty();
        fuelTypes = new ArrayList<>();
    }

}
