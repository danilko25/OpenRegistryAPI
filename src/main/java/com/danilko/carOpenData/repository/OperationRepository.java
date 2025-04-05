package com.danilko.carOpenData.repository;

import com.danilko.carOpenData.entity.Operation;
import com.danilko.carOpenData.repository.extension.OperationJdbcRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OperationRepository extends JpaRepository<Operation, Integer>{
    Optional<Operation> findById(Integer id);
}
