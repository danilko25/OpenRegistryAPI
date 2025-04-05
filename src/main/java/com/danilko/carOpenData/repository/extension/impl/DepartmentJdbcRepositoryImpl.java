package com.danilko.carOpenData.repository.extension.impl;

import com.danilko.carOpenData.entity.Department;
import com.danilko.carOpenData.repository.extension.DepartmentJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DepartmentJdbcRepositoryImpl implements DepartmentJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    @Value("${application.repository.departments_batch_size}")
    private int DEPARTMENTS_BATCH_SIZE;


    @Transactional
    @Override
    public void saveAll(List<Department> departments) {
        jdbcTemplate.batchUpdate("INSERT INTO department (id, name) VALUES (?, ?)",
                departments,
                DEPARTMENTS_BATCH_SIZE,
                (PreparedStatement ps, Department department) -> {
                    ps.setInt(1, department.getId());
                    ps.setString(2, department.getName());
                });
    }

    @Transactional
    @Override
    public void updateAll(List<Department> departments) {
        jdbcTemplate.batchUpdate(
                "UPDATE department SET name = ? WHERE id = ?",
                departments,
                DEPARTMENTS_BATCH_SIZE,
                (PreparedStatement ps, Department department) -> {
                    ps.setString(1, department.getName());
                    ps.setInt(2, department.getId());
                }
        );
    }

}
