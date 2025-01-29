package com.cesantia.elections.controllers;

import com.cesantia.elections.entities.State;
import com.cesantia.elections.services.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/state")
public class StateController {

    @Autowired
    private StateService stateService;

    // Obtener todos los estados
    @GetMapping
    public ResponseEntity<List<State>> getAllStates() {
        List<State> states = stateService.getAllStates();
        return ResponseEntity.ok(states);
    }

    // Obtener un estado por ID
    @GetMapping("/{id}")
    public ResponseEntity<State> getStateById(@PathVariable Long id) {
        return stateService.getStateById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear o actualizar un estado
    @PostMapping
    public ResponseEntity<State> saveState(@RequestBody State state) {
        State savedState = stateService.saveState(state);
        return ResponseEntity.ok(savedState);
    }

    // Eliminar un estado
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteState(@PathVariable Long id) {
        try {
            stateService.deleteState(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
