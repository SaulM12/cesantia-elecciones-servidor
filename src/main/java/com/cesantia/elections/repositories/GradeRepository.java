package com.cesantia.elections.repositories;

import com.cesantia.elections.entities.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade,Long> {
    List<Grade> findByQuadrantId(Long quadrantId);
}
