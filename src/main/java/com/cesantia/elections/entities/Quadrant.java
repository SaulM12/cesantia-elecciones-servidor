package com.cesantia.elections.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Quadrant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "acronym", unique = true)
    private String acronym;

    @Column(nullable = false)
    private Integer quadrantOrder;

    @ManyToMany(fetch = FetchType.EAGER)
    @JsonIgnoreProperties("quadrants") // Evita la recursión infinita
    private Set<ElectionType> electionTypes;
}
