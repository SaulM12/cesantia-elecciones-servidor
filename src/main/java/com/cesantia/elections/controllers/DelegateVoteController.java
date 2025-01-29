package com.cesantia.elections.controllers;

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
    @GetMapping("/candidates-by-quadrant/{quadrantId}")
    public ResponseEntity<List<DelegateVoteCountDto>> getCandidatesWithVoteCountByQuadrantId(
            @PathVariable Long quadrantId) {
        List<DelegateVoteCountDto> candidates = delegateVoteService.getCandidatesWithVoteCountByQuadrantId(quadrantId);
        return ResponseEntity.ok(candidates);
    }
    @GetMapping("/by-delegate-ci/{ci}")
    public ResponseEntity<DelegateVote> getVoteByDelegateCi(@PathVariable String ci) {
        Optional<DelegateVote> vote = delegateVoteService.getVoteByDelegateCi(ci);
        return vote.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

}
