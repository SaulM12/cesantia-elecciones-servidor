package com.cesantia.elections.services;

import com.cesantia.elections.dtos.DelegateVoteCountDto;
import com.cesantia.elections.entities.DelegateVote;
import com.cesantia.elections.repositories.DelegateVoteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class DelegateVoteService {
    @Autowired
    private DelegateVoteRepository delegateVoteRepository;

    // Crear o actualizar un voto
    public DelegateVote saveDelegateVote(DelegateVote delegateVote) {
        String voteControl = UUID.randomUUID().toString();
        delegateVote.setVoteControl(voteControl);
        return delegateVoteRepository.save(delegateVote);
    }

    // Obtener todos los votos
    public List<DelegateVote> getAllDelegateVotes() {
        return delegateVoteRepository.findAll();
    }

    // Obtener un voto por ID
    public Optional<DelegateVote> getDelegateVoteById(Long id) {
        return delegateVoteRepository.findById(id);
    }

    // Eliminar un voto por ID
    public void deleteDelegateVote(Long id) {
        if (delegateVoteRepository.existsById(id)) {
            delegateVoteRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("DelegateVote with ID " + id + " does not exist.");
        }
    }

    /**
     * Retrieves candidates with their vote count by quadrant ID.
     *
     * @param quadrantId the ID of the quadrant
     * @return a list of candidates with their vote counts
     */
    public List<DelegateVoteCountDto> getCandidatesWithVoteCountByQuadrantAndElectionType(Long quadrantId, Long electionTypeId) {
        List<Object[]> results = delegateVoteRepository.findCandidatesWithVoteCountByQuadrantAndElectionType(quadrantId, electionTypeId);

        return results.stream()
                .map(result -> new DelegateVoteCountDto(
                        (Long) result[0], // id del candidato
                        (String) result[1], // nombre completo
                        (Long) result[2] // cantidad de votos
                ))
                .collect(Collectors.toList());
    }

    public Optional<DelegateVote> getVoteByDelegateCiAndElectionType(String ci, Long electionTypeId) {
        return delegateVoteRepository.findByDelegate_CiAndElectionType_Id(ci,electionTypeId);
    }

    public void resetElectionInQuadrant(Long electionTypeId, Long quadrantId) {
        delegateVoteRepository.deleteVotesByElectionTypeAndQuadrant(electionTypeId, quadrantId);
    }
}
