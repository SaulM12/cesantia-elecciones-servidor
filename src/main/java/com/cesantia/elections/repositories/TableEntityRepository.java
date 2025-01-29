package com.cesantia.elections.repositories;

import com.cesantia.elections.entities.TableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TableEntityRepository extends JpaRepository<TableEntity,Long> {
    @Query("SELECT t FROM TableEntity t WHERE t.quadrant.id = :quadrantId ORDER BY t.tableNumber")
    List<TableEntity> findByQuadrantId(@Param("quadrantId") Long quadrantId);
}
