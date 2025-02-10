package com.cesantia.elections.controllers;

import com.cesantia.elections.entities.ElectionType;
import com.cesantia.elections.services.ElectionTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/election-types")
public class ElectionTypeController {

    @Autowired
    private ElectionTypeService electionTypeService;

    // Obtener todos los tipos de elecciones
    @GetMapping
    public List<ElectionType> getAllElectionTypes() {
        return electionTypeService.getAllElectionTypes();
    }

    // Obtener un tipo de elección por ID
    @GetMapping("/{id}")
    public ResponseEntity<ElectionType> getElectionTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(electionTypeService.getElectionTypeById(id));
    }

    // Crear un nuevo tipo de elección
    @PostMapping()
    public ResponseEntity<ElectionType> createElectionType(@RequestBody ElectionType electionType) {
        ElectionType createdElectionType = electionTypeService.createElectionType(electionType);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdElectionType);
    }

    // Actualizar un tipo de elección
    @PutMapping("/{id}")
    public ResponseEntity<ElectionType> updateElectionType(@PathVariable Long id, @RequestBody ElectionType electionType) {
        return ResponseEntity.ok(electionTypeService.updateElectionType(id, electionType));
    }

    // Eliminar un tipo de elección
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteElectionType(@PathVariable Long id) {
        electionTypeService.deleteElectionType(id);
        return ResponseEntity.noContent().build();
    }

    // Habilitar un tipo de elección
    @PutMapping("/{id}/enable")
    public ResponseEntity<ElectionType> enableElection(@PathVariable Long id) {
        return ResponseEntity.ok(electionTypeService.enableElectionType(id));
    }

    // Deshabilitar un tipo de elección
    @PutMapping("/{id}/disable")
    public ResponseEntity<ElectionType> disableElection(@PathVariable Long id) {
        return ResponseEntity.ok(electionTypeService.disableElectionType(id));
    }
}
