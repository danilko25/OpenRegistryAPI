package com.danilko.carOpenData.repository.extension;

import com.danilko.carOpenData.entity.WantedVehicle;

import java.util.List;

public interface WantedVehicleJdbcRepository {
    void saveAll(List<WantedVehicle> wantedVehicles);
}
