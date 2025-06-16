package com.danilko.carOpenData.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class WantedVehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("bodynumber")
    private String vin;

    @JsonProperty("brandmodel")
    private String brandModel;

    @JsonProperty("cartype")
    private String carType;

    @JsonProperty("color")
    private String color;

    @JsonProperty("vehiclenumber")
    private String numberPlate;

    @JsonProperty("chassisnumber")
    private String chassisNumber;

    @JsonProperty("enginenumber")
    private String engineNumber;

    @JsonProperty("illegalseizuredate")
    private Date illegalSeizureDate;

    @JsonProperty("organunit")
    private String organUnit;

    @JsonProperty("insertdate")
    private Date insertDate;
}
