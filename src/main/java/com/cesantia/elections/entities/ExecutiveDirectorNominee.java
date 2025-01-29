package com.cesantia.elections.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExecutiveDirectorNominee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 10)
    private String ci;

    @Column(nullable = false)
    private String names;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String secondLastName;

    @Column(nullable = false)
    private String electionName;

    @ManyToOne
    @JoinColumn(name = "period_id", nullable = false)
    private Period period;

    @Lob
    @Column()
    private byte[] image;
}
