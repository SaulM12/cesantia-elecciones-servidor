package com.cesantia.elections.controllers;

import com.cesantia.elections.dtos.QuadrantDto;
import com.cesantia.elections.entities.Quadrant;
import com.cesantia.elections.services.QuadrantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/quadrants")
public class QuadrantController {

    @Autowired
    private QuadrantService quadrantService;

    // Obtener todos los cuadrantes
    @GetMapping
    public ResponseEntity<List<Quadrant>> getAllQuadrants() {
        List<Quadrant> quadrants = quadrantService.getAllQuadrants();
        return ResponseEntity.ok(quadrants);
    }

    // Obtener un cuadrante por ID
    @GetMapping("/{id}")
    public ResponseEntity<Quadrant> getQuadrantById(@PathVariable Long id) {
        return quadrantService.getQuadrantById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear o actualizar un cuadrante
    @PostMapping
    public ResponseEntity<Quadrant> saveQuadrant(@Valid @RequestBody Quadrant quadrant, BindingResult bindingResult) {
        Quadrant savedQuadrant = quadrantService.saveQuadrant(quadrant);
        return ResponseEntity.ok(savedQuadrant);
    }

    @GetMapping("/with-tables-and-invitations")
    public ResponseEntity<List<QuadrantDto>> getQuadrantsWithTablesAndInvitations() {
        List<QuadrantDto> quadrants = quadrantService.getAllQuadrantsWithTablesAndInvitations();
        return ResponseEntity.ok(quadrants);
    }

    // Eliminar un cuadrante
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuadrant(@PathVariable Long id) {
        try {
            quadrantService.deleteQuadrant(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
