package com.cesantia.elections.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElectionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String description;

    @Column
    private String articles;

    @Column(nullable = false)
    private Boolean enabled = false; // Indica si la elección está habilitada o no

    @ManyToMany
    @JoinTable(
            joinColumns = @JoinColumn(name = "election_type_id"),
            inverseJoinColumns = @JoinColumn(name = "quadrant_id")
    )
    @JsonIgnoreProperties("electionTypes") // Evita la recursión infinita
    private Set<Quadrant> quadrants;
}
