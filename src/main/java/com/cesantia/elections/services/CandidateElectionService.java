package com.cesantia.elections.services;

import com.cesantia.elections.entities.CandidateElection;
import com.cesantia.elections.entities.Delegate;
import com.cesantia.elections.entities.ElectionType;
import com.cesantia.elections.entities.Quadrant;
import com.cesantia.elections.repositories.CandidateElectionRepository;
import com.cesantia.elections.repositories.DelegateRepository;
import com.cesantia.elections.repositories.ElectionTypeRepository;
import com.cesantia.elections.repositories.QuadrantRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CandidateElectionService {
    @Autowired
    private CandidateElectionRepository candidateElectionRepository;

    @Autowired
    private DelegateRepository delegateRepository;

    @Autowired
    private ElectionTypeRepository electionTypeRepository;

    @Autowired
    private QuadrantRepository quadrantRepository;

    public CandidateElection assignCandidate(Long delegateId, Long electionTypeId, Long quadrantId) {
        Delegate delegate = delegateRepository.findById(delegateId)
                .orElseThrow(() -> new RuntimeException("Delegate not found"));
        ElectionType electionType = electionTypeRepository.findById(electionTypeId)
                .orElseThrow(() -> new RuntimeException("ElectionType not found"));
        Quadrant quadrant = quadrantRepository.findById(quadrantId)
                .orElseThrow(() -> new RuntimeException("Quadrant not found"));

        // Crear nueva relación de candidato
        CandidateElection candidateElection = new CandidateElection();
        candidateElection.setDelegate(delegate);
        candidateElection.setElectionType(electionType);
        candidateElection.setQuadrant(quadrant);
        candidateElection.setActive(true);

        return candidateElectionRepository.save(candidateElection);
    }

    public List<CandidateElection> getCandidatesByElectionTypeAndQuadrant(Long electionTypeId, Long quadrantId) {
        return candidateElectionRepository.findByElectionTypeIdAndQuadrantId(electionTypeId, quadrantId);
    }
}
