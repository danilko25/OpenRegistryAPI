package com.danilko.carOpenData.dto.readDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WantedVehicleReadDto {
    private String vin;
    private String brandModel;
    private String carType;
    private String color;
    private String numberPlate;
    private String chassisNumber;
    private String engineNumber;
    private LocalDateTime illegalSeizureDate;
    private String organUnit;
    private LocalDateTime insertDate;
}
