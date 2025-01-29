package com.cesantia.elections.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data

@NoArgsConstructor
@AllArgsConstructor
public class Table {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "table_number", nullable = false)
    private Integer tableNumber;

    @ManyToOne
    @JoinColumn(name = "quadrant_id", nullable = false)
    private Quadrant quadrant;
}
