package com.danilko.carOpenData.repository;

import com.danilko.carOpenData.entity.statistic.StatisticFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface StatisticFilterRepository extends JpaRepository<StatisticFilter, Long> {

    Optional<StatisticFilter> findById(Long id);

    List<StatisticFilter> findByIdIn(List<Long> ids);




}
