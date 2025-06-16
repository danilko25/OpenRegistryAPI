package com.danilko.carOpenData.repository;

import com.danilko.carOpenData.entity.WantedVehicle;
import com.danilko.carOpenData.repository.extension.WantedVehicleJdbcRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface WantedVehiclesRepository extends CrudRepository<WantedVehicle, String>, WantedVehicleJdbcRepository {
    Optional<WantedVehicle> findByVin(String vin);
    Optional<WantedVehicle> findByChassisNumber(String chassisNumber);
    Optional<WantedVehicle> findByNumberPlate(String numberPlate);
}
