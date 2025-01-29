package com.cesantia.elections.controllers;

import com.cesantia.elections.entities.Period;
import com.cesantia.elections.services.PeriodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/period")
public class PeriodController {
    @Autowired
    private PeriodService periodService;

    // Obtener todos los períodos
    @GetMapping
    public ResponseEntity<List<Period>> getAllPeriods() {
        List<Period> periods = periodService.getAllPeriods();
        return ResponseEntity.ok(periods);
    }

    // Obtener un período por ID
    @GetMapping("/{id}")
    public ResponseEntity<Period> getPeriodById(@PathVariable Long id) {
        return periodService.getPeriodById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear o actualizar un período
    @PostMapping
    public ResponseEntity<Period> savePeriod(@RequestBody Period period) {
        Period savedPeriod = periodService.savePeriod(period);
        return ResponseEntity.ok(savedPeriod);
    }

    // Eliminar un período
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePeriod(@PathVariable Long id) {
        try {
            periodService.deletePeriod(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
