package com.cesantia.elections.services;

import com.cesantia.elections.entities.DelegateType;
import com.cesantia.elections.repositories.DelegateTypeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DelegateTypeService {
    @Autowired
    private DelegateTypeRepository delegateTypeRepository;

    // Crear o actualizar un tipo de delegado
    public DelegateType saveDelegateType(DelegateType delegateType) {
        return delegateTypeRepository.save(delegateType);
    }

    // Obtener todos los tipos de delegado
    public List<DelegateType> getAllDelegateTypes() {
        return delegateTypeRepository.findAll();
    }

    // Obtener un tipo de delegado por ID
    public Optional<DelegateType> getDelegateTypeById(Long id) {
        return delegateTypeRepository.findById(id);
    }

    // Eliminar un tipo de delegado por ID
    public void deleteDelegateType(Long id) {
        if (delegateTypeRepository.existsById(id)) {
            delegateTypeRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("DelegateType with ID " + id + " does not exist.");
        }
    }
}
