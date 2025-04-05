package com.danilko.carOpenData.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String person;
    private String regAddr;
    private LocalDate regDate;
    @ManyToOne(fetch = FetchType.LAZY)
    private Operation operation;
    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "vehicle_vin")
    private Vehicle vehicle ;
    private String numberPlate;
}
