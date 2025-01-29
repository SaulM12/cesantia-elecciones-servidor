package com.cesantia.elections.services;

import com.cesantia.elections.entities.State;
import com.cesantia.elections.repositories.StateRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class StateService {

    @Autowired
    private StateRepository stateRepository;

    // Crear o actualizar un State
    public State saveState(State state) {
        return stateRepository.save(state);
    }

    // Obtener todos los States
    public List<State> getAllStates() {
        return stateRepository.findAll();
    }

    // Obtener un State por ID
    public Optional<State> getStateById(Long id) {
        return stateRepository.findById(id);
    }

    // Eliminar un State por ID
    public void deleteState(Long id) {
        if (stateRepository.existsById(id)) {
            stateRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("State with ID " + id + " does not exist.");
        }
    }
}
