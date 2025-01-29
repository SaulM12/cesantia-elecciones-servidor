package com.cesantia.elections.repositories;

import com.cesantia.elections.entities.ExecutiveDirectorNominee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExecutiveDirectorNomineeRepository extends JpaRepository<ExecutiveDirectorNominee, Long> {
    List<ExecutiveDirectorNominee> findByPeriodId(Long periodId);
}
