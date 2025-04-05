package com.danilko.carOpenData.repository.extension;

import com.danilko.carOpenData.entity.Department;

import java.util.List;

public interface DepartmentJdbcRepository {
    void saveAll(List<Department> departments);

    void updateAll(List<Department> departments);
}
