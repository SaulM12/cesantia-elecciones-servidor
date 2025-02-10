package com.cesantia.elections.repositories;

import com.cesantia.elections.entities.CandidateElection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CandidateElectionRepository extends JpaRepository<CandidateElection,Long> {
    List<CandidateElection> findByElectionTypeIdAndQuadrantId(Long electionTypeId, Long quadrantId);
}
