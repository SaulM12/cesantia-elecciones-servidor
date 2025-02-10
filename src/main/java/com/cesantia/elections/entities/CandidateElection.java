package com.cesantia.elections.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "candidate_election")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CandidateElection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "delegate_id", nullable = false)
    private Delegate delegate;

    @ManyToOne
    @JoinColumn(name = "election_type_id", nullable = false)
    private ElectionType electionType;

    @ManyToOne
    @JoinColumn(name = "quadrant_id", nullable = false)
    private Quadrant quadrant;

    @Column(nullable = false)
    private Boolean active = true; // Indica si sigue como candidato
}
