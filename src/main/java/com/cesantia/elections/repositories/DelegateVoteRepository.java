package com.cesantia.elections.repositories;

import com.cesantia.elections.dtos.DelegateVoteCountDto;
import com.cesantia.elections.entities.DelegateVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DelegateVoteRepository extends JpaRepository<DelegateVote,Long> {
    @Query("SELECT c.id, " +
            "CONCAT(c.names), " +
            "COALESCE(COUNT(dv), 0) " +
            "FROM Delegate c " +
            "LEFT JOIN CandidateElection ce ON ce.delegate.id = c.id " +
            "LEFT JOIN DelegateVote dv ON dv.candidate.id = c.id AND dv.electionType.id = :electionTypeId " +
            "WHERE ce.quadrant.id = :quadrantId " +
            "AND ce.electionType.id = :electionTypeId " +
            "AND ce.active = true " +
            "GROUP BY c.id, c.names " +
            "ORDER BY COALESCE(COUNT(dv), 0) DESC")
    List<Object[]> findCandidatesWithVoteCountByQuadrantAndElectionType(
            @Param("quadrantId") Long quadrantId,
            @Param("electionTypeId") Long electionTypeId);
    Optional<DelegateVote> findByDelegate_CiAndElectionType_Id(String ci, Long electionTypeId);
    @Modifying
    @Query("DELETE FROM DelegateVote dv WHERE dv.electionType.id = :electionTypeId AND dv.delegate.grade.quadrant.id = :quadrantId")
    void deleteVotesByElectionTypeAndQuadrant(@Param("electionTypeId") Long electionTypeId, @Param("quadrantId") Long quadrantId);

}
