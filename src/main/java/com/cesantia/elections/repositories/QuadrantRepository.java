package com.cesantia.elections.repositories;

import com.cesantia.elections.dtos.QuadrantDto;
import com.cesantia.elections.entities.Quadrant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuadrantRepository extends JpaRepository<Quadrant,Long> {
    List<Quadrant> findAllByOrderByQuadrantOrderAsc();

}
