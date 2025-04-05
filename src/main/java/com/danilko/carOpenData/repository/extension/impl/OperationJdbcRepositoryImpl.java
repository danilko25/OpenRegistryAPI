package com.danilko.carOpenData.repository.extension.impl;

import com.danilko.carOpenData.entity.Operation;
import com.danilko.carOpenData.repository.extension.OperationJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OperationJdbcRepositoryImpl implements OperationJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    @Value("${application.repository.operations_batch_size}")
    private int OPERATIONS_BATCH_SIZE;


    @Transactional
    @Override
    public void saveAll(List<Operation> operations) {
        jdbcTemplate.batchUpdate("INSERT INTO operation (id, name) VALUES (?, ?)",
                operations,
                OPERATIONS_BATCH_SIZE,
                (PreparedStatement ps, Operation operation) -> {
                    ps.setInt(1, operation.getId());
                    ps.setString(2, operation.getName());
                });
    }

    @Transactional
    @Override
    public void updateAll(List<Operation> operations) {
        jdbcTemplate.batchUpdate(
                "UPDATE operation SET name = ? WHERE id = ?",
                operations,
                OPERATIONS_BATCH_SIZE,
                (PreparedStatement ps, Operation operation) -> {
                    ps.setString(1, operation.getName());
                    ps.setInt(2, operation.getId());
                }
        );
    }

}
