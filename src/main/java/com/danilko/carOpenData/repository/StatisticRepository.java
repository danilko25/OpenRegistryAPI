package com.danilko.carOpenData.repository;

import com.danilko.carOpenData.entity.statistic.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StatisticRepository extends JpaRepository<Statistic, Long> {

    Optional<Statistic> findById(Long id);

    List<Statistic> findAllByFilterIdAndFromDateGreaterThanEqualAndToDateLessThanEqual(Long id, LocalDate fromDate, LocalDate toDate);


    List<Statistic> findAllByFilterIdAndFromDateEqualsAndToDateEquals(Long id, LocalDate fromDate, LocalDate toDate);

    void deleteAllByFilterId(Long filterId);

    void deleteAllByFilterIdAndFromDateGreaterThanEqual(Long id, LocalDate fromDate);
}
