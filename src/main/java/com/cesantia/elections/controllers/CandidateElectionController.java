package com.cesantia.elections.controllers;

import com.cesantia.elections.entities.CandidateElection;
import com.cesantia.elections.services.CandidateElectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/candidates")
public class CandidateElectionController {
    @Autowired
    private CandidateElectionService candidateElectionService;

    @PostMapping("/assign")
    public ResponseEntity<CandidateElection> assignCandidate(
            @RequestParam Long delegateId,
            @RequestParam Long electionTypeId,
            @RequestParam Long quadrantId) {
        CandidateElection candidateElection = candidateElectionService.assignCandidate(delegateId, electionTypeId, quadrantId);
        return ResponseEntity.ok(candidateElection);
    }

    @GetMapping("/by-election-quadrant")
    public ResponseEntity<List<CandidateElection>> getCandidates(
            @RequestParam Long electionTypeId,
            @RequestParam Long quadrantId) {
        List<CandidateElection> candidates = candidateElectionService.getCandidatesByElectionTypeAndQuadrant(electionTypeId, quadrantId);
        return ResponseEntity.ok(candidates);
    }
}
