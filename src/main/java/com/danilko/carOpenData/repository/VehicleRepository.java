package com.danilko.carOpenData.repository;

import com.danilko.carOpenData.entity.Vehicle;
import com.danilko.carOpenData.repository.extension.VehicleJdbcRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface VehicleRepository extends JpaRepository<Vehicle, String>, VehicleJdbcRepository {
    Optional<Vehicle> findByVin(String vin);
}
