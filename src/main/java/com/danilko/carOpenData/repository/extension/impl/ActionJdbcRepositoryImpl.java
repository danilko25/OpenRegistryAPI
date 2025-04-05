package com.danilko.carOpenData.repository.extension.impl;

import com.danilko.carOpenData.entity.Action;
import com.danilko.carOpenData.repository.extension.ActionJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ActionJdbcRepositoryImpl implements ActionJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    @Value("${application.repository.actions_batch_size}")
    private int ACTIONS_BATCH_SIZE;


    @Transactional
    @Override
    public void saveAll(List<Action> actions) {
        jdbcTemplate.batchUpdate("INSERT INTO action (department_id, operation_id, reg_date, reg_addr, vehicle_vin, person, number_plate) VALUES (?, ?, ?, ?, ?, ?, ?)",
                actions,
                ACTIONS_BATCH_SIZE,
                (PreparedStatement ps, Action action) ->{
                    ps.setInt(1, action.getDepartment().getId());
                    ps.setInt(2, action.getOperation().getId());
                    ps.setDate(3, Date.valueOf(action.getRegDate()));
                    ps.setString(4, action.getRegAddr());
                    ps.setString(5, action.getVehicle().getVin());
                    ps.setString(6, action.getPerson());
                    ps.setString(7, action.getNumberPlate());
                } );
    }
}
