package com.cesantia.elections.controllers;

import com.cesantia.elections.dtos.ApiMessage;
import com.cesantia.elections.dtos.DelegateVoteCountDto;
import com.cesantia.elections.entities.DelegateVote;
import com.cesantia.elections.services.DelegateVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/delegate-vote")
public class DelegateVoteController {
    @Autowired
    private DelegateVoteService delegateVoteService;

    // Obtener todos los votos
    @GetMapping
    public ResponseEntity<List<DelegateVote>> getAllDelegateVotes() {
        List<DelegateVote> delegateVotes = delegateVoteService.getAllDelegateVotes();
        return ResponseEntity.ok(delegateVotes);
    }

    // Obtener un voto por ID
    @GetMapping("/{id}")
    public ResponseEntity<DelegateVote> getDelegateVoteById(@PathVariable Long id) {
        return delegateVoteService.getDelegateVoteById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear o actualizar un voto
    @PostMapping
    public ResponseEntity<DelegateVote> saveDelegateVote(@RequestBody DelegateVote delegateVote) {
        DelegateVote savedDelegateVote = delegateVoteService.saveDelegateVote(delegateVote);
        return ResponseEntity.ok(savedDelegateVote);
    }

    // Eliminar un voto por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDelegateVote(@PathVariable Long id) {
        try {
            delegateVoteService.deleteDelegateVote(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves candidates with their vote count by quadrant ID.
     *
     * @param quadrantId the ID of the quadrant
     * @return a list of candidates with their vote counts
     */
    @GetMapping("/candidates")
    public ResponseEntity<List<DelegateVoteCountDto>> getCandidatesWithVotes(
            @RequestParam Long quadrantId,
            @RequestParam Long electionTypeId) {
        List<DelegateVoteCountDto> candidates = delegateVoteService.getCandidatesWithVoteCountByQuadrantAndElectionType(quadrantId, electionTypeId);
        return ResponseEntity.ok(candidates);
    }
    @GetMapping("/by-delegate-ci/{ci}/{electionTypeId}")
    public ResponseEntity<DelegateVote> getVoteByDelegateCi(@PathVariable String ci, @PathVariable Long electionTypeId) {
        Optional<DelegateVote> vote = delegateVoteService.getVoteByDelegateCiAndElectionType(ci,electionTypeId);
        return vote.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/reset-election/{electionTypeId}/quadrant/{quadrantId}")
    public ResponseEntity<ApiMessage> resetElection(
            @PathVariable Long electionTypeId,
            @PathVariable Long quadrantId) {
        delegateVoteService.resetElectionInQuadrant(electionTypeId, quadrantId);
        return ResponseEntity.ok(new ApiMessage("Elección reiniciada correctamente en el cuadrante."));
    }

}
