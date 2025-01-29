package com.cesantia.elections.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chair_number", nullable = false)
    private Integer chairNumber;

    @ManyToOne
    @JoinColumn(name = "table_id", nullable = false)
    private Table table;

    @OneToOne(mappedBy = "chair", cascade = CascadeType.ALL)
    private Delegate delegate;
}
