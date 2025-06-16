package com.danilko.carOpenData.service;

import com.danilko.carOpenData.entity.Department;
import java.util.Map;

public interface DepartmentService {

    Map<Integer, Department> loadAll();

}
