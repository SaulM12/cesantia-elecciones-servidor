package com.cesantia.elections.repositories;

import com.cesantia.elections.entities.ElectionAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElectionAuthorityRepository extends JpaRepository<ElectionAuthority, Long> {
    @Query("SELECT ea FROM ElectionAuthority ea " +
            "WHERE ea.electionType.id = :electionTypeId " +
            "AND ea.quadrant.id = :quadrantId")
    List<ElectionAuthority> findAuthoritiesByElectionTypeAndQuadrant(
            @Param("electionTypeId") Long electionTypeId,
            @Param("quadrantId") Long quadrantId
    );

    @Query("SELECT ea FROM ElectionAuthority ea " +
            "WHERE ea.electionType.id = :electionTypeId " +
            "AND ea.quadrant.id = :quadrantId")
    List<ElectionAuthority> findByElectionTypeAndQuadrant(
            @Param("electionTypeId") Long electionTypeId,
            @Param("quadrantId") Long quadrantId);
}
