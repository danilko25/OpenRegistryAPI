package com.danilko.carOpenData.dto.utilDto;

import com.danilko.carOpenData.entity.Action;
import com.danilko.carOpenData.entity.Department;
import com.danilko.carOpenData.entity.Operation;
import com.danilko.carOpenData.entity.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class RegistrationsRegistryDto {

    List<Action> newActions = new ArrayList<>();

    List<Operation> newOperations = new ArrayList<>();
    List<Operation> operationsToUpdate = new ArrayList<>();

    List<Department> newDepartments = new ArrayList<>();
    List<Department> departmentsToUpdate = new ArrayList<>();

    List<Vehicle> newVehicles = new ArrayList<>();
    List<Vehicle> vehiclesToUpdate = new ArrayList<>();

    public List<Action> getNewActions() {
        return newActions;
    }

    public void setNewActions(List<Action> newActions) {
        this.newActions = newActions;
    }

    public List<Operation> getNewOperations() {
        return newOperations;
    }

    public void setNewOperations(List<Operation> newOperations) {
        this.newOperations = newOperations;
    }

    public List<Operation> getOperationsToUpdate() {
        return operationsToUpdate;
    }

    public void setOperationsToUpdate(List<Operation> operationsToUpdate) {
        this.operationsToUpdate = operationsToUpdate;
    }

    public List<Department> getNewDepartments() {
        return newDepartments;
    }

    public void setNewDepartments(List<Department> newDepartments) {
        this.newDepartments = newDepartments;
    }

    public List<Department> getDepartmentsToUpdate() {
        return departmentsToUpdate;
    }

    public void setDepartmentsToUpdate(List<Department> departmentsToUpdate) {
        this.departmentsToUpdate = departmentsToUpdate;
    }

    public List<Vehicle> getNewVehicles() {
        return newVehicles;
    }

    public void setNewVehicles(List<Vehicle> newVehicles) {
        this.newVehicles = newVehicles;
    }

    public List<Vehicle> getVehiclesToUpdate() {
        return vehiclesToUpdate;
    }

    public void setVehiclesToUpdate(List<Vehicle> vehiclesToUpdate) {
        this.vehiclesToUpdate = vehiclesToUpdate;
    }
}
