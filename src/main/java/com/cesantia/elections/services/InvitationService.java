package com.cesantia.elections.services;

import com.cesantia.elections.entities.Delegate;
import com.cesantia.elections.entities.Invitation;
import com.cesantia.elections.enums.InvitationStatus;
import com.cesantia.elections.repositories.DelegateRepository;
import com.cesantia.elections.repositories.InvitationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class InvitationService {
    @Autowired
    private InvitationRepository invitationRepository;
    @Autowired
    private DelegateRepository delegateRepository;

    // Crear o actualizar una invitación
    public Invitation saveInvitation(Invitation invitation) {
        return invitationRepository.save(invitation);
    }

    // Obtener todas las invitaciones
    public List<Invitation> getAllInvitations() {
        return invitationRepository.findAll();
    }
    public List<Invitation> getInvitationsWithDelegates() {
        return invitationRepository.findByDelegateIsNotNull();
    }

    // Obtener una invitación por ID
    public Optional<Invitation> getInvitationById(Long id) {
        return invitationRepository.findById(id);
    }

    // Eliminar una invitación por ID
    public void deleteInvitation(Long id) {
        if (invitationRepository.existsById(id)) {
            invitationRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Invitation with ID " + id + " does not exist.");
        }
    }

    public void updateInvitationWithDelegate(Long invitationId, Long delegateId) {
        // Buscar la invitación por ID
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new EntityNotFoundException("Invitation not found with id: " + invitationId));

        // Verificar si ya tiene un delegado asociado
        if (invitation.getDelegate() != null) {
            throw new IllegalStateException("Invitation already has a delegate assigned");
        }

        // Buscar el delegado por ID
        Delegate delegate = delegateRepository.findById(delegateId)
                .orElseThrow(() -> new EntityNotFoundException("Delegate not found with id: " + delegateId));

        // Asociar el delegado a la invitación
        invitation.setDelegate(delegate);

        // Cambiar el estado de la invitación (opcional)
        invitation.setStatus(InvitationStatus.PENDING);

        // Guardar la invitación actualizada
        invitationRepository.save(invitation);
    }

    public Invitation getInvitationByDelegateCi(String ci) {
        return invitationRepository.findByDelegate_Ci(ci)
                .orElseThrow(() -> new NoSuchElementException("No se encontró una invitación para el delegado con CI: " + ci));
    }
    public Invitation confirmScan(Long invitationId) {
        // Buscar la invitación por ID
        Invitation invitation = invitationRepository.findById(invitationId)
                .orElseThrow(() -> new RuntimeException("Invitation not found"));

        // Verificar el estado actual y actualizar si es necesario
        if (!invitation.getStatus().equals(InvitationStatus.SCANNED)) {
            invitation.setStatus(InvitationStatus.SCANNED);
            invitation.setScanned(true);
            invitationRepository.save(invitation);
        }

        return invitation;
    }

    public long countTotalInvitations() {
        return invitationRepository.count();
    }

    public long countScannedInvitations() {
        return invitationRepository.countByStatus(InvitationStatus.SCANNED);
    }

}
