package com.cesantia.elections.controllers;

import com.cesantia.elections.dtos.ApiMessage;
import com.cesantia.elections.entities.Invitation;
import com.cesantia.elections.services.InvitationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invitation")
public class InvitationController {
    @Autowired
    private InvitationService invitationService;

    // Obtener todas las invitaciones
    @GetMapping
    public ResponseEntity<List<Invitation>> getAllInvitations() {
        List<Invitation> invitations = invitationService.getAllInvitations();
        return ResponseEntity.ok(invitations);
    }

    @GetMapping("/with-delegates")
    public ResponseEntity<List<Invitation>> getInvitationsWithDelegates() {
        List<Invitation> invitations = invitationService.getInvitationsWithDelegates();
        return ResponseEntity.ok(invitations);
    }

    @GetMapping("/by-delegate-ci/{ci}")
    public ResponseEntity<Invitation> getInvitationByDelegateCi(@PathVariable String ci) {
        Invitation invitation = invitationService.getInvitationByDelegateCi(ci);
        return ResponseEntity.ok(invitation);
    }

    // Obtener una invitación por ID
    @GetMapping("/{id}")
    public ResponseEntity<Invitation> getInvitationById(@PathVariable Long id) {
        return invitationService.getInvitationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear o actualizar una invitación
    @PostMapping
    public ResponseEntity<Invitation> saveInvitation(@RequestBody Invitation invitation) {
        Invitation savedInvitation = invitationService.saveInvitation(invitation);
        return ResponseEntity.ok(savedInvitation);
    }

    // Eliminar una invitación
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvitation(@PathVariable Long id) {
        try {
            invitationService.deleteInvitation(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{invitationId}/assign-delegate/{delegateId}")
    public ResponseEntity<ApiMessage> assignDelegateToInvitation(
            @PathVariable Long invitationId,
            @PathVariable Long delegateId
    ) {
        invitationService.updateInvitationWithDelegate(invitationId, delegateId);
        return ResponseEntity.ok(new ApiMessage("Actualizado"));
    }

    @PutMapping("/{id}/scan")
    public ResponseEntity<Invitation> confirmScan(@PathVariable Long id) {
        Invitation updatedInvitation = invitationService.confirmScan(id);
        return ResponseEntity.ok(updatedInvitation);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getTotalInvitations() {
        long totalInvitations = invitationService.countTotalInvitations();
        return ResponseEntity.ok(totalInvitations);
    }

    // Endpoint para contar el total de invitaciones con estado SCANNED
    @GetMapping("/count/scanned")
    public ResponseEntity<Long> getScannedInvitations() {
        long scannedInvitations = invitationService.countScannedInvitations();
        return ResponseEntity.ok(scannedInvitations);
    }
}
