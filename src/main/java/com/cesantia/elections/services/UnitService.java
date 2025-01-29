package com.cesantia.elections.services;


import com.cesantia.elections.entities.Unit;
import com.cesantia.elections.repositories.UnitRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UnitService {

    @Autowired
    private UnitRepository unitRepository;

    // Crear o actualizar una unidad
    public Unit saveUnit(Unit unit) {
        return unitRepository.save(unit);
    }

    // Obtener todas las unidades
    public List<Unit> getAllUnits() {
        return unitRepository.findAll();
    }

    // Obtener una unidad por ID
    public Optional<Unit> getUnitById(Long id) {
        return unitRepository.findById(id);
    }

    // Eliminar una unidad por ID
    public void deleteUnit(Long id) {
        if (unitRepository.existsById(id)) {
            unitRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Unit with ID " + id + " does not exist.");
        }
    }
}
