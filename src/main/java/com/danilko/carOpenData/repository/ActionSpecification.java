package com.danilko.carOpenData.repository;

import com.danilko.carOpenData.dto.utilDto.ActionFilterDto;
import com.danilko.carOpenData.entity.Action;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class ActionSpecification implements Specification<Action> {
    private final ActionFilterDto actionFilterDto;

    @Override
    public Predicate toPredicate(Root<Action> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> exps = new ArrayList<>();

        Join<Object, Object> vehicleJoin = (Join<Object, Object>)root.fetch("vehicle");
        Join<Object, Object> operationJoin = (Join<Object, Object>)root.fetch("operation");
        Join<Object, Object> departmentJoin = (Join<Object, Object>)root.fetch("department");

        actionFilterDto.getFromDate().ifPresent(date -> exps.add(criteriaBuilder.greaterThanOrEqualTo(root.get("regDate"), date)));
        actionFilterDto.getToDate().ifPresent(date -> exps.add(criteriaBuilder.lessThanOrEqualTo(root.get("regDate"), date)));

        if(!actionFilterDto.getDepartmentCodes().isEmpty()){
            exps.add(departmentJoin.get("id").in(actionFilterDto.getDepartmentCodes()));
        }
        if (!actionFilterDto.getOperationCodes().isEmpty()){
            exps.add(operationJoin.get("id").in(actionFilterDto.getOperationCodes()));
        }
        actionFilterDto.getNumPlate().ifPresent(numPlate -> exps.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("numberPlate")), '%' + numPlate.toUpperCase() + '%')));

        var vehicleFilterDto = actionFilterDto.getVehicle();
        vehicleFilterDto.getVin().ifPresent(vin->exps.add(criteriaBuilder.like(criteriaBuilder.upper(vehicleJoin.get("vin")), '%' + vin.toUpperCase() + '%')));
        vehicleFilterDto.getBrand().ifPresent(brand->exps.add(criteriaBuilder.like(criteriaBuilder.upper(vehicleJoin.get("brand")), brand.toUpperCase())));
        vehicleFilterDto.getModel().ifPresent(model->exps.add(criteriaBuilder.like(criteriaBuilder.upper(vehicleJoin.get("model")), model.toUpperCase())));
        vehicleFilterDto.getMakeYearFrom().ifPresent(yearFrom->exps.add(criteriaBuilder.ge(vehicleJoin.get("makeYear"), yearFrom)));
        vehicleFilterDto.getMakeYearTo().ifPresent(yearTo->exps.add(criteriaBuilder.le(vehicleJoin.get("makeYear"), yearTo)));
        vehicleFilterDto.getColor().ifPresent(color->exps.add(criteriaBuilder.like(criteriaBuilder.upper(vehicleJoin.get("color")), color.toUpperCase())));
        vehicleFilterDto.getKind().ifPresent(kind->exps.add(criteriaBuilder.like(criteriaBuilder.upper(vehicleJoin.get("kind")), kind.toUpperCase())));

        if(!vehicleFilterDto.getFuelTypes().isEmpty()){
            exps.add(vehicleJoin.get("fuelType").in(vehicleFilterDto.getFuelTypes()));
        }
        vehicleFilterDto.getEngineCapacityFrom().ifPresent(engineCapacity->exps.add(criteriaBuilder.ge(vehicleJoin.get("engineCapacity"), engineCapacity)));
        vehicleFilterDto.getEngineCapacityTo().ifPresent(engineCapacity->exps.add(criteriaBuilder.le(vehicleJoin.get("engineCapacity"), engineCapacity)));

        return criteriaBuilder.and(exps.toArray(new Predicate[0]));
    }
}

