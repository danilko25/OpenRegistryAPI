package com.danilko.carOpenData.repository;

import com.danilko.carOpenData.entity.statistic.StatisticReport;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StatisticReportRepository extends JpaRepository<StatisticReport, Long> {

    Optional<StatisticReport> findById(Long id);
}
