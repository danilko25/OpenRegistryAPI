package com.danilko.carOpenData.repository.extension.impl;

import com.danilko.carOpenData.entity.Vehicle;
import com.danilko.carOpenData.repository.extension.VehicleJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.List;

@Component
@RequiredArgsConstructor
public class VehicleJdbcRepositoryImpl implements VehicleJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    @Value("${application.repository.vehicles_batch_size}")
    private int VEHICLES_BATCH_SIZE;


    @Transactional
    @Override
    public void saveAll(List<Vehicle> vehicles) {
        jdbcTemplate.batchUpdate("INSERT INTO vehicle (vin, purpose, model, kind, fuel_type, color, brand, body_type, total_weight, own_weight, make_year, engine_capacity) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)",
                vehicles,
                VEHICLES_BATCH_SIZE,
                (PreparedStatement ps, Vehicle vehicle) -> {
                    ps.setString(1, vehicle.getVin());
                    ps.setString(2, vehicle.getPurpose());
                    ps.setString(3, vehicle.getModel());
                    ps.setString(4, vehicle.getKind());
                    ps.setString(5, vehicle.getFuelType());
                    ps.setString(6, vehicle.getColor());
                    ps.setString(7, vehicle.getBrand());
                    ps.setString(8, vehicle.getBodyType());
                    ps.setDouble(9, vehicle.getTotalWeight());
                    ps.setDouble(10, vehicle.getOwnWeight());
                    ps.setInt(11, vehicle.getMakeYear());
                    ps.setInt(12, vehicle.getEngineCapacity());
                });
    }

    @Transactional
    @Override
    public void updateAll(List<Vehicle> vehicles) {
        jdbcTemplate.batchUpdate(
                "UPDATE vehicle SET purpose = ?, model = ?, kind = ?, fuel_type = ?, color = ?, brand = ?, body_type = ?, total_weight = ?, own_weight = ?, make_year = ?, engine_capacity = ? WHERE vin = ?",
                vehicles,
                VEHICLES_BATCH_SIZE,
                (PreparedStatement ps, Vehicle vehicle) -> {
                    ps.setString(1, vehicle.getPurpose());
                    ps.setString(2, vehicle.getModel());
                    ps.setString(3, vehicle.getKind());
                    ps.setString(4, vehicle.getFuelType());
                    ps.setString(5, vehicle.getColor());
                    ps.setString(6, vehicle.getBrand());
                    ps.setString(7, vehicle.getBodyType());
                    ps.setDouble(8, vehicle.getTotalWeight());
                    ps.setDouble(9, vehicle.getOwnWeight());
                    ps.setInt(10, vehicle.getMakeYear());
                    ps.setInt(11, vehicle.getEngineCapacity());
                    ps.setString(12, vehicle.getVin());
                }
        );
    }

}
