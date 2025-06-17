package com.danilko.carOpenData.updatedDataDownloading;

import com.danilko.carOpenData.entity.Action;
import com.danilko.carOpenData.entity.Department;
import com.danilko.carOpenData.entity.Operation;
import com.danilko.carOpenData.entity.Vehicle;
import com.danilko.carOpenData.repository.ActionRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest
class TestOfNewParsingAndSavingTest {

    @Autowired
    ActionRepository actionRepository;

    @Autowired
    EntityManager entityManager;


    @Test
    void savingTest(){
        var res = actionRepository.findFirst1000By();
        List<Action> resToSave = new ArrayList<>((int)(res.size()/0.75+1));
        for (Action action : res){
            resToSave.add(copyAction(action, 6));
        }
        entityManager.clear();
        var startOfSaving1000Actions = System.currentTimeMillis();
        actionRepository.saveAll(resToSave);
        var endOfSaving1000Actions = System.currentTimeMillis();
        res = actionRepository.findFirst10000By();
        resToSave = new ArrayList<>((int)(res.size()/0.75+1));
        for (Action action : res){
            resToSave.add(copyAction(action, new Random().nextInt(30) + 1));
        }
        entityManager.clear();
        var startOfSaving10000Actions = System.currentTimeMillis();
        actionRepository.saveAll(resToSave);
        var endOfSaving10000Actions = System.currentTimeMillis();

        res = actionRepository.findFirst100000By();
        resToSave = new ArrayList<>((int)(res.size()/0.75+1));
        for (Action action : res){
            resToSave.add(copyAction(action, new Random().nextInt(30) + 1));
        }
        entityManager.clear();
        var startOfSaving100000Actions = System.currentTimeMillis();
        actionRepository.saveAll(resToSave);
        var endOfSaving100000Actions = System.currentTimeMillis();

        res = actionRepository.findFirst1000000By();
        resToSave = new ArrayList<>((int)(res.size()/0.75+1));
        for (Action action : res){
            resToSave.add(copyAction(action, new Random().nextInt(30) + 1));
        }
        entityManager.clear();
        var startOfSaving1000000Actions = System.currentTimeMillis();
        actionRepository.saveAll(resToSave);
        var endOfSaving1000000Actions = System.currentTimeMillis();

        System.out.println(
                        "Час збереження 1000 записів = " + (endOfSaving1000Actions - startOfSaving1000Actions) / 1000.0 + " секунд\n" +
                        "Час збереження 10000 записів = " + (endOfSaving10000Actions - startOfSaving10000Actions) / 1000.0 + " секунд\n" +
                        "Час збереження 100000 записів = " + (endOfSaving100000Actions - startOfSaving100000Actions) / 1000.0 + " секунд\n" +
                        "Час збереження 1000000 записів = " + (endOfSaving1000000Actions - startOfSaving1000000Actions) / 1000.0 + " секунд"
        );
    }

    private Action copyAction(Action action, int modifyRegDate){
        return new Action(null ,action.getPerson(), action.getRegAddr(), action.getRegDate().minusDays(modifyRegDate), copyOperation(action.getOperation()), copyDepartment(action.getDepartment()), copyVehicle(action.getVehicle()), action.getNumberPlate());
    }

    private Vehicle copyVehicle(Vehicle vehicle) {
        return new Vehicle(vehicle.getVin(), vehicle.getBrand(), vehicle.getModel(), vehicle.getMakeYear(), vehicle.getColor(), vehicle.getKind(), vehicle.getBodyType(), vehicle.getPurpose(), vehicle.getFuelType(), vehicle.getEngineCapacity(), vehicle.getOwnWeight(), vehicle.getTotalWeight());
    }

    private Department copyDepartment(Department department) {
        return new Department(department.getId(), department.getName());
    }

    private Operation copyOperation(Operation operation) {
        return new Operation(operation.getId(), operation.getName());
    }
}