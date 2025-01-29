package com.cesantia.elections.services;

import com.cesantia.elections.dtos.QuadrantDto;
import com.cesantia.elections.entities.Invitation;
import com.cesantia.elections.entities.Quadrant;
import com.cesantia.elections.entities.TableEntity;
import com.cesantia.elections.repositories.InvitationRepository;
import com.cesantia.elections.repositories.QuadrantRepository;
import com.cesantia.elections.repositories.TableEntityRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class QuadrantService {

    @Autowired
    private QuadrantRepository quadrantRepository;
    @Autowired
    private TableEntityRepository tableEntityRepository;
    @Autowired
    private InvitationRepository invitationRepository;

    // Crear o actualizar un Quadrant
    public Quadrant saveQuadrant(Quadrant quadrant) {
        return quadrantRepository.save(quadrant);
    }

    // Obtener todos los Quadrants
    public List<Quadrant> getAllQuadrants() {
        return quadrantRepository.findAllByOrderByQuadrantOrderAsc();
    }

    // Obtener un Quadrant por ID
    public Optional<Quadrant> getQuadrantById(Long id) {
        return quadrantRepository.findById(id);
    }

    // Listado de cuadrantes con mesas y sillas
    public List<QuadrantDto> getAllQuadrantsWithTablesAndInvitations() {
        List<Quadrant> quadrants = quadrantRepository.findAllByOrderByQuadrantOrderAsc();

        List<QuadrantDto> quadrantDtos = new ArrayList<>();

        for (Quadrant quadrant : quadrants) {
            QuadrantDto quadrantDto = new QuadrantDto(
                    quadrant.getId(),
                    quadrant.getDescription(),
                    quadrant.getAcronym(),
                    quadrant.getQuadrantOrder()
            );

            List<TableEntity> tables = tableEntityRepository.findByQuadrantId(quadrant.getId());
            for (TableEntity table : tables) {
                QuadrantDto.TableDto tableDto = new QuadrantDto.TableDto(
                        table.getId(),
                        table.getTableNumber()
                );

                List<Invitation> invitations = invitationRepository.findByTableEntityId(table.getId());
                for (Invitation invitation : invitations) {
                    tableDto.getInvitations().add(new QuadrantDto.InvitationDto(
                            invitation.getId(),
                            invitation.getChairNumber(),
                            invitation.getStatus(),
                            invitation.getDelegate()
                    ));
                }

                quadrantDto.getTables().add(tableDto);
            }

            quadrantDtos.add(quadrantDto);
        }

        return quadrantDtos;
    }

    // Eliminar un Quadrant por ID
    public void deleteQuadrant(Long id) {
        if (quadrantRepository.existsById(id)) {
            quadrantRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Quadrant with ID " + id + " does not exist.");
        }
    }
}
