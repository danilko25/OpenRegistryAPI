package com.danilko.carOpenData.repository;

import com.danilko.carOpenData.dto.utilDto.VehicleFilterDto;
import com.danilko.carOpenData.entity.Vehicle;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class VehicleSpecification implements Specification<Vehicle> {

    private final VehicleFilterDto vehicleFilterDto;

    @Override
    public Predicate toPredicate(Root<Vehicle> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> exps = new ArrayList<>();

        vehicleFilterDto.getVin().ifPresent(vin->exps.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("vin")), '%' + vin.toLowerCase() + '%')));
        vehicleFilterDto.getBrand().ifPresent(brand->exps.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("brand")), brand.toLowerCase())));
        vehicleFilterDto.getModel().ifPresent(model->exps.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("model")), model.toLowerCase())));
        vehicleFilterDto.getMakeYearFrom().ifPresent(yearFrom->exps.add(criteriaBuilder.ge(root.get("make_year"), yearFrom)));
        vehicleFilterDto.getMakeYearTo().ifPresent(yearTo->exps.add(criteriaBuilder.le(root.get("make_year"), yearTo)));
        vehicleFilterDto.getColor().ifPresent(color->exps.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("color")), color.toLowerCase())));
        vehicleFilterDto.getKind().ifPresent(kind->exps.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("kind")), kind.toLowerCase())));

        if(!vehicleFilterDto.getFuelTypes().isEmpty()){
            exps.add(root.get("fuelType").in(vehicleFilterDto.getFuelTypes()));
        }

        vehicleFilterDto.getEngineCapacityFrom().ifPresent(engineCapacity->exps.add(criteriaBuilder.ge(root.get("engine_capacity"), engineCapacity)));
        vehicleFilterDto.getEngineCapacityTo().ifPresent(engineCapacity->exps.add(criteriaBuilder.le(root.get("engine_capacity"), engineCapacity)));

        return criteriaBuilder.and(exps.toArray(new Predicate[0]));
    }
}
