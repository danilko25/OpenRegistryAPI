package com.danilko.carOpenData.repository;

import com.danilko.carOpenData.entity.Department;
import com.danilko.carOpenData.repository.extension.DepartmentJdbcRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Integer>, DepartmentJdbcRepository {
    Optional<Department> findById(Integer id);
}
