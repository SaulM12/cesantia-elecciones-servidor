package com.cesantia.elections.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "abbreviation", nullable = false)
    private String abbreviation;

    @Column
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "quadrant_id", nullable = false)
    private Quadrant quadrant;
}
