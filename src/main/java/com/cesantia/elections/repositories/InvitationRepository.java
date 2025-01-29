package com.cesantia.elections.repositories;

import com.cesantia.elections.entities.Invitation;
import com.cesantia.elections.enums.InvitationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InvitationRepository extends JpaRepository<Invitation,Long> {
    @Query("SELECT i FROM Invitation i WHERE i.tableEntity.id = :tableId ORDER BY i.chairNumber")
    List<Invitation> findByTableEntityId(@Param("tableId") Long tableId);
    @Query("SELECT i.delegate.id FROM Invitation i WHERE i.delegate IS NOT NULL")
    List<Long> findAllDelegatesWithInvitation();
    Optional<Invitation> findByDelegate_Ci(String ci);
    List<Invitation> findByDelegateIsNotNull();

    // Contar el total de invitaciones
    long count();

    // Contar el total de invitaciones con estado SCANNED
    long countByStatus(InvitationStatus status);
}
