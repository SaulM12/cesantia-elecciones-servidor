package com.cesantia.elections.controllers;

import com.cesantia.elections.dtos.ApiMessage;
import com.cesantia.elections.dtos.ExecutiveDirectorVoteCountDto;
import com.cesantia.elections.entities.Delegate;
import com.cesantia.elections.entities.ExecutiveDirectorVote;
import com.cesantia.elections.services.ExecutiveDirectorVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("/api/director-votes")
public class ExecutiveDirectorVoteController {

    @Autowired
    private ExecutiveDirectorVoteService voteService;

    @PostMapping("/{nomineeId}/vote/{userIp}")
    public ResponseEntity<Void> castVote(@PathVariable Long nomineeId,@PathVariable String userIp, @RequestBody Delegate delegate) {
        String voteControl = UUID.randomUUID().toString();
        voteService.castVote(nomineeId, delegate, voteControl, userIp);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/count")
    public ResponseEntity<List<ExecutiveDirectorVoteCountDto>> countVotesByNominee() {
        List<ExecutiveDirectorVoteCountDto> voteCounts = voteService.countVotesByNominee();
        return ResponseEntity.ok(voteCounts);
    }
    @GetMapping("/delegate/{ci}")
    public ResponseEntity<ExecutiveDirectorVote> getVoteByDelegateCi(@PathVariable String ci) {
        try {
            ExecutiveDirectorVote vote = voteService.getVoteByDelegateCi(ci);
            return ResponseEntity.ok(vote);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/reset")
    public ResponseEntity<ApiMessage> resetExecutiveDirectorVotes() {
        voteService.deleteAllExecutiveDirectorVotes();
        return ResponseEntity.ok(new ApiMessage("Todos los votos de Executive Director han sido eliminados."));
    }
}
