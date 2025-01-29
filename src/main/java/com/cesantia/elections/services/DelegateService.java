package com.cesantia.elections.services;

import com.cesantia.elections.entities.Delegate;
import com.cesantia.elections.entities.Role;
import com.cesantia.elections.entities.User;
import com.cesantia.elections.enums.RoleList;
import com.cesantia.elections.repositories.DelegateRepository;
import com.cesantia.elections.repositories.InvitationRepository;
import com.cesantia.elections.repositories.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class DelegateService {
    @Autowired
    private DelegateRepository delegateRepository;

    @Autowired
    private UserService userService;
    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Crear un delegado y su usuario asociado
    public Delegate createDelegateAndUser(Delegate delegate) {
        if (userService.existsByCi(delegate.getCi())) {
            throw new IllegalArgumentException("El usuario con esta cédula ya existe.");
        }

        // Crear y guardar el usuario
        Role roleUser = roleRepository.findByName(RoleList.ROLE_DELEGATE)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));
        User user = new User();
        user.setCi(delegate.getCi());
        user.setPassword(passwordEncoder.encode(delegate.getCi())); // Contraseña = CI
        user.setRole(roleUser);
        user.setActive(true);

        userService.save(user);

        // Guardar el delegado
        return delegateRepository.save(delegate);
    }

    // Actualizar datos del delegado y sincronizar con su usuario
    public Delegate updateDelegateAndUser(Long id, Delegate updatedDelegate) {
        Delegate existingDelegate = delegateRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Delegado no encontrado."));

        // Actualizar campos del delegado
        existingDelegate.setNames(updatedDelegate.getNames());
        existingDelegate.setLastName(updatedDelegate.getLastName());
        existingDelegate.setSecondLastName(updatedDelegate.getSecondLastName());
        existingDelegate.setPhone(updatedDelegate.getPhone());
        existingDelegate.setHomePhone(updatedDelegate.getHomePhone());
        existingDelegate.setEmail(updatedDelegate.getEmail());
        existingDelegate.setCandidate(updatedDelegate.getCandidate());
        existingDelegate.setCompleteInfo(updatedDelegate.getCompleteInfo());
        existingDelegate.setGrade(updatedDelegate.getGrade());
        existingDelegate.setDelegateType(updatedDelegate.getDelegateType());
        existingDelegate.setUnit(updatedDelegate.getUnit());

        // Actualizar el usuario asociado
        User user = userService.findByCi(existingDelegate.getCi())
                .orElseThrow(() -> new IllegalArgumentException("Usuario asociado no encontrado."));
        user.setCi(existingDelegate.getCi());
        userService.save(user);

        return delegateRepository.save(existingDelegate);
    }

    // Desactivar usuario
    public void deactivateUser(String ci) {
        User user = userService.findByCi(ci)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado."));
        user.setActive(false);
        userService.save(user);
    }

    public List<Delegate> getAll(){
        return delegateRepository.findAll();
    }

    public List<Delegate> getDelegatesWithoutInvitationByQuadrant(Long quadrantId) {
        List<Delegate> delegatesInQuadrant = delegateRepository.findByGrade_Quadrant_Id(quadrantId);

        // Obtener los IDs de los delegados con invitaciones
        List<Long> delegateIdsWithInvitation = invitationRepository.findAllDelegatesWithInvitation();

        // Filtrar los delegados que no tienen invitaciones
        return delegatesInQuadrant.stream()
                .filter(delegate -> !delegateIdsWithInvitation.contains(delegate.getId()))
                .toList();
    }

    public Delegate getDelegateByCi(String ci) {
        return delegateRepository.findByCi(ci)
                .orElseThrow(() -> new NoSuchElementException("No se encontró un delegado con el CI: " + ci));
    }
    public void updateEnableToVoteForAllDelegates(Boolean status) {
        delegateRepository.updateEnableToVoteForAll(status);
    }

    public void setCandidateToTrue(String ci) {
        int updatedRows = delegateRepository.updateCandidateToTrueByCi(ci);
        if (updatedRows == 0) {
            throw new IllegalArgumentException("No delegate found with CI: " + ci);
        }
    }


    public Delegate addImageToDelegate(String ci, byte[] image) {
        Delegate delegate = delegateRepository.findByCi(ci)
                .orElseThrow(() -> new EntityNotFoundException("Delegate with CI " + ci + " not found"));

        delegate.setImage(image);
        return delegateRepository.save(delegate);
    }

}
