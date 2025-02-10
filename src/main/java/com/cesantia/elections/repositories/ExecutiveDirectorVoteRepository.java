package com.cesantia.elections.repositories;

import com.cesantia.elections.dtos.ExecutiveDirectorVoteCountDto;
import com.cesantia.elections.entities.ExecutiveDirectorVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExecutiveDirectorVoteRepository extends JpaRepository<ExecutiveDirectorVote, Long> {
    @Query("SELECT n.id, n.ci, n.names, n.electionName, COUNT(v) " +
            "FROM ExecutiveDirectorNominee n " +
            "LEFT JOIN ExecutiveDirectorVote v ON v.nominee = n " +
            "GROUP BY n.id, n.ci, n.names, n.electionName")
    List<Object[]> countVotesByNominee();

    Optional<ExecutiveDirectorVote> findByDelegate_Ci(String ci);

    @Modifying
    @Query("DELETE FROM ExecutiveDirectorVote")
    void deleteAllVotes();
}
