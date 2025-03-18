package com.cesantia.elections.repositories;

import com.cesantia.elections.dtos.DelegateVoteCountDto;
import com.cesantia.elections.entities.DelegateVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DelegateVoteRepository extends JpaRepository<DelegateVote,Long> {
    @Query("SELECT c.id, " +
            "CONCAT(c.names), " +
            "COALESCE(COUNT(dv), 0) " +
            "FROM Delegate c " +
            "LEFT JOIN CandidateElection ce ON ce.delegate.id = c.id " +
            "LEFT JOIN DelegateVote dv ON dv.candidate.id = c.id AND dv.electionType.id = :electionTypeId " +
            "WHERE ce.quadrant.id = :quadrantId " +
            "AND ce.electionType.id = :electionTypeId " +
            "AND ce.active = true " +
            "GROUP BY c.id, c.names " +
            "ORDER BY COALESCE(COUNT(dv), 0) DESC")
    List<Object[]> findCandidatesWithVoteCountByQuadrantAndElectionType(
            @Param("quadrantId") Long quadrantId,
            @Param("electionTypeId") Long electionTypeId);
    Optional<DelegateVote> findByDelegate_CiAndElectionType_Id(String ci, Long electionTypeId);
    @Modifying
    @Query("DELETE FROM DelegateVote dv WHERE dv.electionType.id = :electionTypeId AND dv.delegate.grade.quadrant.id = :quadrantId")
    void deleteVotesByElectionTypeAndQuadrant(@Param("electionTypeId") Long electionTypeId, @Param("quadrantId") Long quadrantId);

    @Query("SELECT c.ci, " +  // Cédula del delegado
            "g.description, " + // Grado del delegado
            "CONCAT(c.names), " + // Nombre completo
            "COALESCE(COUNT(dv), 0) " +  // Conteo de votos
            "FROM Delegate c " +
            "LEFT JOIN c.grade g " +  // Relación con el grado
            "LEFT JOIN CandidateElection ce ON ce.delegate.id = c.id " +  // Candidatura
            "LEFT JOIN DelegateVote dv ON dv.candidate.id = c.id AND dv.electionType.id = :electionTypeId " +  // Votos del candidato
            "WHERE ce.quadrant.id = :quadrantId " +  // Filtrar por cuadrante
            "AND ce.electionType.id = :electionTypeId " +  // Filtrar por tipo de elección
            "AND ce.active = true " +  // Solo candidatos activos
            "GROUP BY c.ci, g.description, c.names " +  // Agrupación
            "ORDER BY COALESCE(COUNT(dv), 0) DESC")  // Orden por número de votos
    List<Object[]> findCandidatesWithVoteCount(
            @Param("quadrantId") Long quadrantId,
            @Param("electionTypeId") Long electionTypeId);


}
