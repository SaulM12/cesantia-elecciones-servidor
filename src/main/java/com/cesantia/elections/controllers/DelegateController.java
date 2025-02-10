package com.cesantia.elections.controllers;

import com.cesantia.elections.dtos.ApiMessage;
import com.cesantia.elections.entities.Delegate;
import com.cesantia.elections.services.DelegateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/delegate")
public class DelegateController {
    @Autowired
    private DelegateService delegateService;

    //Obtener todos los delegados
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<Delegate>> getAll() {
        return ResponseEntity.ok(delegateService.getAll());
    }

    // Crear un delegado y su usuario asociado
    @PostMapping
    public ResponseEntity<Delegate> createDelegate(@RequestBody Delegate delegate) {
        Delegate createdDelegate = delegateService.createDelegateAndUser(delegate);
        return ResponseEntity.ok(createdDelegate);
    }

    // Actualizar datos del delegado y sincronizar con su usuario
    @PutMapping("/{id}")
    public ResponseEntity<Delegate> updateDelegate(@PathVariable Long id, @RequestBody Delegate updatedDelegate) {
        Delegate delegate = delegateService.updateDelegateAndUser(id, updatedDelegate);
        return ResponseEntity.ok(delegate);
    }

    // Desactivar usuario asociado a un delegado
    @PutMapping("/{ci}/deactivate")
    public ResponseEntity<Void> deactivateUser(@PathVariable String ci) {
        delegateService.deactivateUser(ci);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/quadrant/{quadrantId}/without-invitation")
    public ResponseEntity<List<Delegate>> getDelegatesWithoutInvitationByQuadrant(
            @PathVariable Long quadrantId) {
        List<Delegate> delegates = delegateService.getDelegatesWithoutInvitationByQuadrant(quadrantId);
        return ResponseEntity.ok(delegates);
    }

    @GetMapping("/by-ci/{ci}")
    public ResponseEntity<Delegate> getDelegateByCi(@PathVariable String ci) {
        Delegate delegate = delegateService.getDelegateByCi(ci);
        return ResponseEntity.ok(delegate);
    }

    @PutMapping("/enable-to-vote")
    public ResponseEntity<ApiMessage> updateEnableToVote(@RequestParam Boolean status) {
        delegateService.updateEnableToVoteForAllDelegates(status);
        return ResponseEntity.ok(new ApiMessage("Estado de votación actualizado correctamente"));
    }

    /**
     * Endpoint to set the candidate property to true for a delegate by their CI.
     *
     * @param ci The CI of the delegate.
     * @return ResponseEntity indicating success or failure.
     */
    @PutMapping("/{ci}/set-candidate")
    public ResponseEntity<ApiMessage> setCandidateToTrue(@PathVariable String ci) {
        try {
            delegateService.setCandidateToTrue(ci);
            return ResponseEntity.ok(new ApiMessage("El delegado con CI " + ci + " ahora es un candidato."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiMessage("No encotrado"));
        }
    }

    @PostMapping("/import")
    public ResponseEntity<String> importDelegates(@RequestParam("file") MultipartFile file) {
        delegateService.importDelegatesFromCSV(file);
        return ResponseEntity.ok("Archivo CSV procesado correctamente");
    }

}
