package com.danilko.carOpenData.service;

import com.danilko.carOpenData.entity.Operation;

import java.util.Map;

public interface OperationService {

    Map<Integer, Operation> loadAll();
}
