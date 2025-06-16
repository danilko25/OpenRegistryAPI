package com.danilko.carOpenData.service.impl;

import com.danilko.carOpenData.entity.Operation;
import com.danilko.carOpenData.repository.OperationRepository;
import com.danilko.carOpenData.service.OperationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class OperationServiceImpl implements OperationService {

    private final OperationRepository repository;

    @Override
    public Map<Integer, Operation> loadAll() {
        List<Operation> operations = repository.findAll();
        int size = operations.size();

        Map<Integer, Operation> operationMap = new HashMap<>((int)(size/0.75+1));
        for (Operation op : operations) {
            operationMap.put(op.getId(), op);
        }
        return operationMap;
    }
}
