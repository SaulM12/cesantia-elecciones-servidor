package com.cesantia.elections.services;

import com.cesantia.elections.entities.Grade;
import com.cesantia.elections.repositories.GradeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GradeService {

    @Autowired
    private GradeRepository gradeRepository;

    // Crear o actualizar un Grade
    public Grade saveGrade(Grade grade) {
        return gradeRepository.save(grade);
    }

    // Obtener todos los Grades
    public List<Grade> getAllGrades() {
        return gradeRepository.findAll();
    }

    // Obtener un Grade por ID
    public Optional<Grade> getGradeById(Long id) {
        return gradeRepository.findById(id);
    }

    // Eliminar un Grade por ID
    public void deleteGrade(Long id) {
        if (gradeRepository.existsById(id)) {
            gradeRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Grade with ID " + id + " does not exist.");
        }
    }
}
