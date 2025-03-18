package com.cesantia.elections.controllers;

import com.cesantia.elections.entities.ElectionAuthority;
import com.cesantia.elections.services.ElectionAuthorityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/election-authorities")
public class ElectionAuthorityController {

    private final ElectionAuthorityService electionAuthorityService;

    public ElectionAuthorityController(ElectionAuthorityService electionAuthorityService) {
        this.electionAuthorityService = electionAuthorityService;
    }

    @GetMapping
    public ResponseEntity<List<ElectionAuthority>> getAllAuthorities() {
        return ResponseEntity.ok(electionAuthorityService.getAllAuthorities());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ElectionAuthority> getAuthorityById(@PathVariable Long id) {
        return ResponseEntity.ok(electionAuthorityService.getAuthorityById(id));
    }

    @GetMapping("/by-election-and-quadrant")
    public ResponseEntity<List<ElectionAuthority>> getByElectionAndQuadrant(
            @RequestParam Long electionTypeId, @RequestParam Long quadrantId) {
        return ResponseEntity.ok(
                electionAuthorityService.getAuthoritiesByElectionTypeAndQuadrant(electionTypeId, quadrantId));
    }

    @PostMapping
    public ResponseEntity<ElectionAuthority> createAuthority(@RequestBody ElectionAuthority authority) {
        return ResponseEntity.ok(electionAuthorityService.createAuthority(authority));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ElectionAuthority> updateAuthority(@PathVariable Long id,
                                                             @RequestBody ElectionAuthority authority) {
        return ResponseEntity.ok(electionAuthorityService.updateAuthority(id, authority));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAuthority(@PathVariable Long id) {
        electionAuthorityService.deleteAuthority(id);
        return ResponseEntity.noContent().build();
    }
}
