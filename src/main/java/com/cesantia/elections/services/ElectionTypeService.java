package com.cesantia.elections.services;

import com.cesantia.elections.entities.ElectionType;
import com.cesantia.elections.entities.Quadrant;
import com.cesantia.elections.repositories.ElectionTypeRepository;
import com.cesantia.elections.repositories.QuadrantRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ElectionTypeService {

    @Autowired
    private ElectionTypeRepository electionTypeRepository;

    @Autowired
    private QuadrantRepository quadrantRepository;

    // Obtener todos los tipos de elecciones
    public List<ElectionType> getAllElectionTypes() {
        return electionTypeRepository.findAll();
    }

    // Obtener un tipo de elección por ID
    public ElectionType getElectionTypeById(Long id) {
        return electionTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ElectionType not found"));
    }


    // Crear un nuevo tipo de elección
    public ElectionType createElectionType(ElectionType electionType) {
        return electionTypeRepository.save(electionType);
    }

    // Actualizar un tipo de elección
    public ElectionType updateElectionType(Long id, ElectionType updatedElectionType) {
        ElectionType existingElectionType = getElectionTypeById(id);
        existingElectionType.setName(updatedElectionType.getName());
        existingElectionType.setEnabled(updatedElectionType.getEnabled());
        existingElectionType.setDescription(updatedElectionType.getDescription());
        existingElectionType.setArticles(updatedElectionType.getArticles());
        return electionTypeRepository.save(existingElectionType);
    }

    // Eliminar un tipo de elección
    public void deleteElectionType(Long id) {
        electionTypeRepository.deleteById(id);
    }

    // Habilitar un tipo de elección
    public ElectionType enableElectionType(Long id) {
        ElectionType electionType = getElectionTypeById(id);
        electionType.setEnabled(true);
        return electionTypeRepository.save(electionType);
    }

    // Deshabilitar un tipo de elección
    public ElectionType disableElectionType(Long id) {
        ElectionType electionType = getElectionTypeById(id);
        electionType.setEnabled(false);
        return electionTypeRepository.save(electionType);
    }
}
