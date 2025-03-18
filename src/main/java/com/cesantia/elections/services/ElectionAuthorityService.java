package com.cesantia.elections.services;

import com.cesantia.elections.entities.ElectionAuthority;
import com.cesantia.elections.repositories.ElectionAuthorityRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ElectionAuthorityService {
    @Autowired
    private ElectionAuthorityRepository electionAuthorityRepository;

    public List<ElectionAuthority> getAllAuthorities() {
        return electionAuthorityRepository.findAll();
    }

    public ElectionAuthority getAuthorityById(Long id) {
        return electionAuthorityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autoridad no encontrada"));
    }

    public List<ElectionAuthority> getAuthoritiesByElectionTypeAndQuadrant(Long electionTypeId, Long quadrantId) {
        return electionAuthorityRepository.findByElectionTypeAndQuadrant(electionTypeId, quadrantId);
    }

    public ElectionAuthority createAuthority(ElectionAuthority authority) {
        return electionAuthorityRepository.save(authority);
    }

    public ElectionAuthority updateAuthority(Long id, ElectionAuthority updatedAuthority) {
        ElectionAuthority authority = electionAuthorityRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autoridad no encontrada"));

        // Actualizar los valores de la autoridad existente
        authority.setRole(updatedAuthority.getRole());
        authority.setFullName(updatedAuthority.getFullName());
        authority.setGrade(updatedAuthority.getGrade());
        authority.setCi(updatedAuthority.getCi());
        authority.setPhone(updatedAuthority.getPhone());
        authority.setElectionType(updatedAuthority.getElectionType());
        authority.setQuadrant(updatedAuthority.getQuadrant());

        return electionAuthorityRepository.save(authority);
    }

    public void deleteAuthority(Long id) {
        if (!electionAuthorityRepository.existsById(id)) {
            throw new RuntimeException("Autoridad no encontrada");
        }
        electionAuthorityRepository.deleteById(id);
    }
}
