package com.cesantia.elections.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ElectionAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "election_type_id")
    private ElectionType electionType; // Relación con el tipo de elección

    @ManyToOne
    @JoinColumn(name = "quadrant_id")
    private Quadrant quadrant; // Relación con el cuadrante

    @Column(nullable = false)
    private String role; // PRESIDENTE o SECRETARIO

    private String fullName;
    private String grade;
    private String ci;
    private String phone;
}

