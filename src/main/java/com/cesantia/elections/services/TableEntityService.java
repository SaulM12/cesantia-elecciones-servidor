package com.cesantia.elections.services;

import com.cesantia.elections.entities.Invitation;
import com.cesantia.elections.entities.TableEntity;
import com.cesantia.elections.enums.InvitationStatus;
import com.cesantia.elections.repositories.InvitationRepository;
import com.cesantia.elections.repositories.TableEntityRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TableEntityService {
    @Autowired
    private TableEntityRepository tableEntityRepository;
    @Autowired
    private InvitationRepository invitationRepository;

    // Crear o actualizar una mesa
    public TableEntity saveTableEntity(TableEntity tableEntity) {

        TableEntity savedTableEntity = tableEntityRepository.save(tableEntity);

        // Crear 8 invitaciones asociadas a la mesa
        for (int i = 1; i <= 8; i++) {
            Invitation invitation = new Invitation();
            invitation.setChairNumber(i); // Número de silla (1 a 8)
            invitation.setTableEntity(savedTableEntity); // Asociar la mesa recién guardada
            invitation.setStatus(InvitationStatus.CREATED); // Estado inicial: CREATED
            invitation.setScanned(false); // Marcado como no escaneado

            // Guardar la invitación en la base de datos
            invitationRepository.save(invitation);
        }

        return savedTableEntity;
    }

    // Obtener todas las mesas
    public List<TableEntity> getAllTableEntities() {
        return tableEntityRepository.findAll();
    }

    // Obtener una mesa por ID
    public Optional<TableEntity> getTableEntityById(Long id) {
        return tableEntityRepository.findById(id);
    }

    // Eliminar una mesa por ID
    public void deleteTableEntity(Long id) {
        if (tableEntityRepository.existsById(id)) {
            tableEntityRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("TableEntity with ID " + id + " does not exist.");
        }
    }
}
