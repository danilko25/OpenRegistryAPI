package com.danilko.carOpenData.repository.extension;

import com.danilko.carOpenData.entity.Vehicle;

import java.util.List;

public interface VehicleJdbcRepository {
    void saveAll(List<Vehicle> vehicles);

    void updateAll(List<Vehicle> vehicles);
}
