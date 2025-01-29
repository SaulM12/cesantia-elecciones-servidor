package com.cesantia.elections.repositories;

import com.cesantia.elections.entities.Period;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PeriodRepository extends JpaRepository<Period,Long> {
}
