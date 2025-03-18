package com.cesantia.elections.services;

import com.cesantia.elections.entities.*;
import com.cesantia.elections.enums.RoleList;
import com.cesantia.elections.repositories.*;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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

    @Autowired
    private GradeRepository gradeRepository;
    @Autowired
    private DelegateTypeRepository delegateTypeRepository;

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
        existingDelegate.setPhone(updatedDelegate.getPhone());
        existingDelegate.setEmail(updatedDelegate.getEmail());
        existingDelegate.setCandidate(updatedDelegate.getCandidate());
        existingDelegate.setGrade(updatedDelegate.getGrade());
        existingDelegate.setDelegateType(updatedDelegate.getDelegateType());

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


    public void importDelegatesFromCSV(MultipartFile file) {
        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            List<String[]> rows = reader.readAll();
            rows.remove(0); // Eliminar la fila de encabezados

            for (String[] row : rows) {
                Delegate delegate = new Delegate();
                delegate.setId(Long.valueOf(row[0]));
                delegate.setCi(row[1]);
                delegate.setNames(row[3]);
                delegate.setPhone(row[8]);
                delegate.setIngresoAsis(row[5]);
                delegate.setDependence(row[12]);
                delegate.setUnitName(row[7]);
                delegate.setEmail(row[9]);
                delegate.setConfirmation(Boolean.parseBoolean(row[11]));

                // Buscar y asignar Grade
                Optional<Grade> gradeOpt = gradeRepository.findById(Long.parseLong(row[2]));
                gradeOpt.ifPresent(delegate::setGrade);

                // Buscar y asignar DelegateType
                Optional<DelegateType> delegateTypeOpt = delegateTypeRepository.findById(Long.parseLong(row[4]));
                delegateTypeOpt.ifPresent(delegate::setDelegateType);

                // Guardar el delegado
                delegateRepository.save(delegate);

                // Crear y guardar el usuario asociado
                createUserForDelegate(delegate);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al procesar el archivo CSV", e);
        }
    }

    private void createUserForDelegate(Delegate delegate) {
        if (!userService.existsByCi(delegate.getCi())) { // Evita duplicados
            Optional<Role> roleOpt = roleRepository.findById(2); // Buscar rol con ID 2
            if (roleOpt.isPresent()) {
                User user = new User();
                user.setCi(delegate.getCi());
                user.setPassword(passwordEncoder.encode(delegate.getCi())); // Cédula como contraseña encriptada
                user.setRole(roleOpt.get());
                userService.save(user);
            } else {
                throw new RuntimeException("El rol con ID 2 no existe");
            }
        }
    }


}
