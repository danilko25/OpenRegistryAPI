package com.danilko.carOpenData.dataDownloading.impl;

import com.danilko.carOpenData.dto.utilDto.RegistrationsRegistryDto;
import com.danilko.carOpenData.entity.Action;
import com.danilko.carOpenData.entity.Department;
import com.danilko.carOpenData.entity.Operation;
import com.danilko.carOpenData.entity.Vehicle;
import com.danilko.carOpenData.exception.ValidationException;
import com.danilko.carOpenData.service.DepartmentService;
import com.danilko.carOpenData.service.OperationService;
import com.danilko.carOpenData.service.VehicleService;
import com.danilko.carOpenData.dataDownloading.service.CsvProcessingService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
@RequiredArgsConstructor
public class CsvProcessingServiceImpl implements CsvProcessingService {
    private final DepartmentService departmentService;
    private final OperationService operationService;
    private final VehicleService vehicleService;
    private final EntityManager entityManager;


    @Transactional
    @Override
    public RegistrationsRegistryDto parseCsvFile(String filePath) {
        try (FileInputStream inputStream = new FileInputStream(filePath); Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8)) {
            if (scanner.hasNext()) scanner.nextLine();

            Map<Integer, Operation> operationsCache = operationService.loadAll();
            Map<Integer, Department> departmentsCache = departmentService.loadAll();
            Map<String, Vehicle> vehiclesCache = vehicleService.loadAll();
            entityManager.clear();

            List<Action> newActions = new ArrayList<>();

            List<Operation> newOperations = new ArrayList<>();
            Map<Integer, Operation> operationsToUpdate = new HashMap<>();

            List<Department> newDepartments = new ArrayList<>();
            Map<Integer, Department> departmentsToUpdate = new HashMap<>();

            List<Vehicle> newVehicles = new ArrayList<>();
            Map<String, Vehicle> vehiclesToUpdate = new HashMap<>();
            DateTimeFormatter formatter = null;

            while (scanner.hasNext()) {

                var row = scanner.nextLine().split(";");
                String person = removeFirstAndLastChar(row[0]);
                String regAddr = removeFirstAndLastChar(row[1]);
                Integer operCode = Integer.parseInt(removeFirstAndLastChar(row[2]));
                String operName = removeFirstAndLastChar(row[3]);
                if (formatter == null) {
                    if (row[4].matches("\\d{2}.\\d{2}.\\d{4}")) {
                        formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                    } else if (row[4].matches("\\d{2}.\\d{2}.\\d{2}")) {
                        formatter = DateTimeFormatter.ofPattern("dd.MM.yy");
                    }else throw new ValidationException("Wrong date format in csv file: " + row[4]);
                }
                LocalDate regDate = LocalDate.parse(row[4], formatter);
                Integer depCode = Integer.parseInt(row[5]);
                String depName = removeFirstAndLastChar(row[6]);
                String brand = removeFirstAndLastChar(row[7]);
                String model = removeFirstAndLastChar(row[8]);
                String vin = removeFirstAndLastChar(row[9]);
                Integer makeYear = Integer.parseInt(row[10]);
                String color = removeFirstAndLastChar(row[11]);
                String kind = removeFirstAndLastChar(row[12]);
                String bodyType = removeFirstAndLastChar(row[13]);
                String purpose = removeFirstAndLastChar(row[14]);
                String fuelType = removeFirstAndLastChar(row[15]);

                //Vehicle doesn't have engineCapacity if it is electric
                Integer engineCapacity = row[16].isEmpty() ? 0 : Integer.parseInt(row[16]);
                Double ownWeight = row[17].isEmpty() ? 0 : Double.parseDouble(row[17].replace(',', '.'));;
                Double totalWeight = row[18].isEmpty() ? 0 : Double.parseDouble(row[18].replace(',', '.'));
                String numberPlate = removeFirstAndLastChar(row[19]);

                Vehicle newVehicle = new Vehicle(vin, brand, model, makeYear, color, kind,
                        bodyType, purpose, fuelType, engineCapacity, ownWeight, totalWeight
                );
                Vehicle existingVehicle = vehiclesCache.get(vin);

                if (existingVehicle != null) {
                    if (!existingVehicle.equals(newVehicle)) {
                        updateVehicle(existingVehicle, newVehicle);

                        var updatedVehicle = vehiclesToUpdate.get(vin);
                        if (updatedVehicle != null) {
                            updateVehicle(updatedVehicle, newVehicle);
                        } else vehiclesToUpdate.put(vin, newVehicle);
                    }
                } else {
                    newVehicles.add(newVehicle);
                    vehiclesCache.put(vin, newVehicle);
                }

                Operation newOperation = new Operation(operCode, operName);
                Operation exisingOperation = operationsCache.get(operCode);

                if (exisingOperation != null) {
                    if (!exisingOperation.equals(newOperation)) {
                        //Якщо операція вже існує в БД
                        exisingOperation.setName(newOperation.getName());

                        //Якщо операція вже оновлювалася в цьому файлі
                        var updatedOperation = operationsToUpdate.get(operCode);
                        if (updatedOperation != null) {
                            updatedOperation.setName(newOperation.getName());
                        } else operationsToUpdate.put(operCode, newOperation);

                    }
                } else {
                    newOperations.add(newOperation);
                    operationsCache.put(operCode, newOperation);
                }

                Department newDepartment = new Department(depCode, depName);
                Department existing = departmentsCache.get(depCode);

                if (existing != null) {
                    if (!existing.equals(newDepartment)) {

                        existing.setName(newDepartment.getName());

                        var updatedDepartment = departmentsToUpdate.get(depCode);
                        if (updatedDepartment != null) {
                            updatedDepartment.setName(newDepartment.getName());
                        } else departmentsToUpdate.put(depCode, newDepartment);
                    }
                } else {
                    newDepartments.add(newDepartment);
                    departmentsCache.put(depCode, newDepartment);
                }

                var action = new Action(null, person, regAddr, regDate, operationsCache.get(operCode),
                        departmentsCache.get(depCode), vehiclesCache.get(vin), numberPlate
                );
                newActions.add(action);
            }


            var registry = new RegistrationsRegistryDto();
            registry.setNewOperations(newOperations);
            registry.setOperationsToUpdate(operationsToUpdate.values().stream().toList());
            registry.setNewDepartments(newDepartments);
            registry.setDepartmentsToUpdate(departmentsToUpdate.values().stream().toList());
            registry.setNewVehicles(newVehicles);
            registry.setVehiclesToUpdate(vehiclesToUpdate.values().stream().toList());
            registry.setNewActions(newActions);
            return registry;

        } catch (IOException e) {
            throw new RuntimeException("Помилка зчитування CSV файлу", e);
        }
    }

    private String removeFirstAndLastChar(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return str.substring(1, str.length() - 1);
    }

    private void updateVehicle(Vehicle vehicleToUpdate, Vehicle newVehicle) {
        vehicleToUpdate.setBrand(newVehicle.getBrand());
        vehicleToUpdate.setModel(newVehicle.getModel());
        vehicleToUpdate.setMakeYear(newVehicle.getMakeYear());
        vehicleToUpdate.setColor(newVehicle.getColor());
        vehicleToUpdate.setKind(newVehicle.getKind());
        vehicleToUpdate.setBodyType(newVehicle.getBodyType());
        vehicleToUpdate.setPurpose(newVehicle.getPurpose());
        vehicleToUpdate.setFuelType(newVehicle.getFuelType());
        vehicleToUpdate.setEngineCapacity(newVehicle.getEngineCapacity());
        vehicleToUpdate.setOwnWeight(newVehicle.getOwnWeight());
        vehicleToUpdate.setTotalWeight(newVehicle.getTotalWeight());
    }
}
