package com.cesantia.elections.repositories;

import com.cesantia.elections.entities.Delegate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DelegateRepository extends JpaRepository<Delegate,Long> {
    List<Delegate> findByGrade_Quadrant_Id(Long quadrantId);

    Optional<Delegate> findByCi(String ci);
    @Modifying
    @Query("UPDATE Delegate d SET d.enableToVote = :status")
    void updateEnableToVoteForAll(@Param("status") Boolean status);

    @Modifying
    @Query("UPDATE Delegate d SET d.candidate = true WHERE d.ci = :ci")
    int updateCandidateToTrueByCi(@Param("ci") String ci); // Cambiado de void a int
}
