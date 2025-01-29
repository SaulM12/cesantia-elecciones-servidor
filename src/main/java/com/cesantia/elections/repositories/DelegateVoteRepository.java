package com.cesantia.elections.repositories;

import com.cesantia.elections.dtos.DelegateVoteCountDto;
import com.cesantia.elections.entities.DelegateVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DelegateVoteRepository extends JpaRepository<DelegateVote,Long> {
    @Query("SELECT c.id, " +
            "CONCAT(c.names, ' ', c.lastName, ' ', c.secondLastName), " +
            "c.image, " +
            "COALESCE(COUNT(dv), 0) " +
            "FROM Delegate c " +
            "LEFT JOIN c.grade g " +
            "LEFT JOIN g.quadrant q " +
            "LEFT JOIN DelegateVote dv ON dv.candidate.id = c.id " +
            "WHERE q.id = :quadrantId " +
            "AND c.candidate = true " +
            "GROUP BY c.id, c.names, c.lastName, c.secondLastName " +
            "ORDER BY COALESCE(COUNT(dv), 0) DESC")
    List<Object[]> findCandidatesWithVoteCountIncludingZeroByQuadrantId(@Param("quadrantId") Long quadrantId);
    Optional<DelegateVote> findByDelegate_Ci(String ci);
}
