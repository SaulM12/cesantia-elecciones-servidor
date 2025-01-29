package com.cesantia.elections.services;

import com.cesantia.elections.entities.Period;
import com.cesantia.elections.repositories.PeriodRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PeriodService {
    @Autowired
    private PeriodRepository periodRepository;

    // Crear o actualizar un período
    public Period savePeriod(Period period) {
        return periodRepository.save(period);
    }

    // Obtener todos los períodos
    public List<Period> getAllPeriods() {
        return periodRepository.findAll();
    }

    // Obtener un período por ID
    public Optional<Period> getPeriodById(Long id) {
        return periodRepository.findById(id);
    }

    // Eliminar un período por ID
    public void deletePeriod(Long id) {
        if (periodRepository.existsById(id)) {
            periodRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Period with ID " + id + " does not exist.");
        }
    }
}
