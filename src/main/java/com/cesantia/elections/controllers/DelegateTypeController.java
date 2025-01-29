package com.cesantia.elections.controllers;

import com.cesantia.elections.entities.DelegateType;
import com.cesantia.elections.services.DelegateTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delegate-type")
public class DelegateTypeController {
    @Autowired
    private DelegateTypeService delegateTypeService;

    // Obtener todos los tipos de delegado
    @GetMapping
    public ResponseEntity<List<DelegateType>> getAllDelegateTypes() {
        List<DelegateType> delegateTypes = delegateTypeService.getAllDelegateTypes();
        return ResponseEntity.ok(delegateTypes);
    }

    // Obtener un tipo de delegado por ID
    @GetMapping("/{id}")
    public ResponseEntity<DelegateType> getDelegateTypeById(@PathVariable Long id) {
        return delegateTypeService.getDelegateTypeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear o actualizar un tipo de delegado
    @PostMapping
    public ResponseEntity<DelegateType> saveDelegateType(@RequestBody DelegateType delegateType) {
        DelegateType savedDelegateType = delegateTypeService.saveDelegateType(delegateType);
        return ResponseEntity.ok(savedDelegateType);
    }

    // Eliminar un tipo de delegado por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDelegateType(@PathVariable Long id) {
        try {
            delegateTypeService.deleteDelegateType(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
