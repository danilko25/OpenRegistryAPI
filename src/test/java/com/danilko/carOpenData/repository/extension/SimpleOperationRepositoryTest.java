package com.danilko.carOpenData.repository.extension;

import com.danilko.carOpenData.entity.Operation;
import com.danilko.carOpenData.repository.OperationRepository;
import com.danilko.carOpenData.repository.extension.impl.OperationJdbcRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;


@SpringBootTest
class SimpleOperationRepositoryTest {


    @Autowired
    OperationJdbcRepositoryImpl operationRepositoryCustom;

    @Autowired
    OperationRepository operationRepository;

    @Test
    void save(){
        List<Operation> operations = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            operations.add(new Operation(i, "Operation " + i));
        }
        operationRepositoryCustom.saveAll(operations);
        operationRepository.findAll().forEach(System.out::println);
    }

}