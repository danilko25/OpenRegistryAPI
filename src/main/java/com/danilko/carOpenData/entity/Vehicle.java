package com.danilko.carOpenData.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Vehicle {
    @Id
    private String vin;
    private String brand;
    private String model;
    private Integer makeYear;
    private String color;
    private String kind;
    private String bodyType;
    private String purpose;
    private String fuelType;
    private Integer engineCapacity;
    private Double ownWeight;
    private Double totalWeight;
}
