package com.cesantia.elections.repositories;

import com.cesantia.elections.entities.ElectionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ElectionTypeRepository extends JpaRepository<ElectionType, Long> {

}
