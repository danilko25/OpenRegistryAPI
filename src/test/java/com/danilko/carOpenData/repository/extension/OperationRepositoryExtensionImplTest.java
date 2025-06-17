package com.danilko.carOpenData.repository.extension;

import com.danilko.carOpenData.entity.Operation;
import com.danilko.carOpenData.repository.OperationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OperationRepositoryExtensionImplTest {

//    @Autowired
//    private OperationRepositoryExtensionImpl operationRepositoryExtension;
//
//    @Autowired
//    private OperationRepository operationRepository;
//
//    @Transactional
//    @Test
//    void persist() {
//        List<Operation> operations = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            operations.add(new Operation(i, "Operation " + i));
//        }
//        operationRepositoryExtension.persistAll(operations);
//        operationRepositoryExtension.flush(); // Это важно!
//    }
//
//    @Transactional
//    @Test
//    void saveAll() {
//        List<Operation> operations = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            operations.add(new Operation(i, "Operation " + i));
//        }
//        operationRepository.saveAll(operations);
//    }
}