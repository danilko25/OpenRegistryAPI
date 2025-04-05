package com.danilko.carOpenData.repository.extension;

import com.danilko.carOpenData.entity.Operation;

import java.util.List;

public interface OperationJdbcRepository {
    void saveAll(List<Operation> operations);

    void updateAll(List<Operation> operations);
}
