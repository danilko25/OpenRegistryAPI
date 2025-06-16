package com.danilko.carOpenData.repository.extension.impl;

import com.danilko.carOpenData.entity.WantedVehicle;
import com.danilko.carOpenData.repository.extension.WantedVehicleJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WantedVehicleJdbcRepositoryImpl implements WantedVehicleJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    @Value("${application.repository.wanted_vehicles_batch_size}")
    private int WANTED_VEHICLES_BATCH_SIZE;

    @Transactional
    @Override
    public void saveAll(List<WantedVehicle> wantedVehicles) {
        jdbcTemplate.batchUpdate(
                "INSERT INTO wanted_vehicle (vin, brand_model, car_type, color, number_plate, chassis_number, engine_number, illegal_seizure_date, organ_unit, insert_date) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                wantedVehicles,
                WANTED_VEHICLES_BATCH_SIZE,
                (PreparedStatement ps, WantedVehicle vehicle) -> {
                    ps.setString(1, vehicle.getVin());
                    ps.setString(2, vehicle.getBrandModel());
                    ps.setString(3, vehicle.getCarType());
                    ps.setString(4, vehicle.getColor());
                    ps.setString(5, vehicle.getNumberPlate());
                    ps.setString(6, vehicle.getChassisNumber());
                    ps.setString(7, vehicle.getEngineNumber());
                    ps.setTimestamp(8, vehicle.getIllegalSeizureDate() != null ? new Timestamp(vehicle.getIllegalSeizureDate().getTime()) : null);
                    ps.setString(9, vehicle.getOrganUnit());
                    ps.setTimestamp(10, vehicle.getInsertDate() != null ? new Timestamp(vehicle.getInsertDate().getTime()) : null);
                }
        );
    }
}
