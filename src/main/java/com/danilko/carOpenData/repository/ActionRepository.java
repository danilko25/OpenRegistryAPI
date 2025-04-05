package com.danilko.carOpenData.repository;

import com.danilko.carOpenData.entity.Action;
import com.danilko.carOpenData.repository.extension.ActionJdbcRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ActionRepository extends JpaRepository<Action, Long>, JpaSpecificationExecutor<Action>, ActionJdbcRepository {

    @Query("select a, v, d, o from Action a join a.department d join a.operation o join a.vehicle v where a.numberPlate = ?1 order by a.regDate desc limit 1")
    Optional<Action> findByNumberPlate(String numberPlate);

    @Query("select a, v, d, o from Action a join a.department d join a.operation o join a.vehicle v where v.vin = ?1 order by a.regDate desc")
    List<Action> findAllByVehicleVin(String vin);

    @Query("select a, v, d, o from Action a join a.department d join a.operation o join a.vehicle v where v.vin = ?1 order by a.regDate desc limit 1")
    Optional<Action> findByVehicleVin(String vin);

    void deleteAllByRegDateIsBetween(LocalDate from, LocalDate to);

}



