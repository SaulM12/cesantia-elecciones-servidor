package com.cesantia.elections.controllers;

import com.cesantia.elections.entities.TableEntity;
import com.cesantia.elections.services.TableEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/table")
public class TableEntityController {
    @Autowired
    private TableEntityService tableEntityService;

    // Obtener todas las mesas
    @GetMapping
    public ResponseEntity<List<TableEntity>> getAllTableEntities() {
        List<TableEntity> tableEntities = tableEntityService.getAllTableEntities();
        return ResponseEntity.ok(tableEntities);
    }

    // Obtener una mesa por ID
    @GetMapping("/{id}")
    public ResponseEntity<TableEntity> getTableEntityById(@PathVariable Long id) {
        return tableEntityService.getTableEntityById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear o actualizar una mesa
    @PostMapping
    public ResponseEntity<TableEntity> saveTableEntity(@RequestBody TableEntity tableEntity) {
        TableEntity savedTableEntity = tableEntityService.saveTableEntity(tableEntity);
        return ResponseEntity.ok(savedTableEntity);
    }

    // Eliminar una mesa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTableEntity(@PathVariable Long id) {
        try {
            tableEntityService.deleteTableEntity(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
