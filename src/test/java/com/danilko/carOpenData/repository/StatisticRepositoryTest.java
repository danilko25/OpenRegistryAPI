package com.danilko.carOpenData.repository;

import com.danilko.carOpenData.entity.statistic.Statistic;
import com.danilko.carOpenData.entity.statistic.StatisticFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class StatisticRepositoryTest {

    @Autowired
    private StatisticRepository statisticRepository;
    @Autowired
    private StatisticFilterRepository statisticFilterRepository;


    @Test
    void testFindAllByFilterIdAndDates() {
        Statistic statistic1 = new Statistic();
        var filter1 = new StatisticFilter();
        filter1.setId(1L);
        statisticFilterRepository.save(filter1);
        statistic1.setFilter(filter1);
        statistic1.setFromDate(LocalDate.of(2024, 1, 10));
        statistic1.setToDate(LocalDate.of(2024, 1, 20));
        statisticRepository.save(statistic1);

        Statistic statistic2 = new Statistic();
        var filter2 = new StatisticFilter();
        filter1.setId(2L);
        statisticFilterRepository.save(filter2);
        statistic2.setFromDate(LocalDate.of(2023, 1, 1));
        statistic2.setToDate(LocalDate.of(2023, 12, 31));
        statisticRepository.save(statistic2);

        List<Statistic> result = statisticRepository.findAllByFilterIdAndFromDateGreaterThanEqualAndToDateLessThanEqual(
                1L, LocalDate.of(2024, 1, 1), LocalDate.of(2024, 1, 30)
        );

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getFromDate()).isEqualTo(LocalDate.of(2024, 1, 10));
    }
}
