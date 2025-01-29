package com.cesantia.elections.repositories;

import com.cesantia.elections.entities.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State,Long> {
}
