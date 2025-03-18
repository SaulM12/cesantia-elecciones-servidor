package com.cesantia.elections.services;

import com.cesantia.elections.dtos.*;
import com.cesantia.elections.repositories.DelegateVoteRepository;
import com.cesantia.elections.repositories.ElectionAuthorityRepository;
import com.cesantia.elections.repositories.QuadrantRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ActaEleccionService {
    private final TemplateEngine templateEngine;
    private final QuadrantRepository quadrantRepository;
    private final DelegateVoteRepository delegateVoteRepository;
    private final ElectionAuthorityRepository electionAuthorityRepository;

    public ActaEleccionService(TemplateEngine templateEngine, QuadrantRepository quadrantRepository, DelegateVoteRepository delegateVoteRepository, ElectionAuthorityRepository electionAuthorityRepository) {
        this.templateEngine = templateEngine;
        this.quadrantRepository = quadrantRepository;
        this.delegateVoteRepository = delegateVoteRepository;
        this.electionAuthorityRepository = electionAuthorityRepository;
    }

    public String generateHtml() {
        List<QuadrantFileDto> quadrants = quadrantRepository.findAll().stream().map(quadrant ->
                new QuadrantFileDto(
                        quadrant.getDescription(),
                        quadrant.getElectionTypes().stream().map(electionType ->
                                new ElectionTypeDto(
                                        electionType.getName(),
                                        electionType.getArticles(),
                                        delegateVoteRepository.findCandidatesWithVoteCount(quadrant.getId(), electionType.getId()).stream().map(candidate ->
                                                new CandidateDto(

                                                        (String) candidate[0],  // Cédula del delegado
                                                        (String) candidate[1],  // Grado
                                                        (String) candidate[2],  // Nombre completo
                                                        ((Number) candidate[3]).intValue() // Conteo de votos
                                                )
                                        ).collect(Collectors.toList()),
                                        electionAuthorityRepository
                                                .findAuthoritiesByElectionTypeAndQuadrant(electionType.getId(), quadrant.getId())
                                                .stream().map(authority ->
                                                        new AuthorityDto(authority.getRole(), authority.getFullName(),
                                                                authority.getGrade(), authority.getCi(), authority.getPhone())
                                                ).collect(Collectors.toList())
                                )
                        ).collect(Collectors.toList())


                )
        ).collect(Collectors.toList());

        ActaEleccionDto acta = new ActaEleccionDto(quadrants);

        Context context = new Context();
        context.setVariable("quadrants", acta.getQuadrants());

        return templateEngine.process("acta-eleccion", context);
    }
}
