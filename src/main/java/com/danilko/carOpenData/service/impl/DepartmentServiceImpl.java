package com.danilko.carOpenData.service.impl;


import com.danilko.carOpenData.entity.Department;
import com.danilko.carOpenData.repository.DepartmentRepository;
import com.danilko.carOpenData.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Override
    public Map<Integer, Department> loadAll() {
        List<Department> departments = departmentRepository.findAll();
        int size = departments.size();
        Map<Integer, Department> departmentMap = new HashMap<>((int)(size/0.75+1));
        for (Department d : departments) {
            departmentMap.put(d.getId(), d);
        }
        return departmentMap;
    }
}
