package com.danilko.carOpenData.entity.statistic;

import com.danilko.carOpenData.entity.Department;
import com.danilko.carOpenData.entity.FuelType;
import com.danilko.carOpenData.entity.Operation;
import com.danilko.carOpenData.entity.VehicleColor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class StatisticFilter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToMany
    private List<Operation> operations;
    @ManyToMany
    private List<Department> departments;

    private String numPlate;
    private String vin;
    private String brand;
    private String model;
    private Integer makeYearFrom;
    private Integer makeYearTo;
    @Enumerated(EnumType.STRING)
    private VehicleColor color;
    private String kind;
    private String bodyType;

    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    private Integer engineCapacityFrom;
    private Integer engineCapacityTo;
}
