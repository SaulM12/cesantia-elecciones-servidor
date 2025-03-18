package com.cesantia.elections.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
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

    @Column()
    private String grade;

    @Column(nullable = false)
    private String electionName;

    @ManyToOne
    @JoinColumn(name = "period_id", nullable = false)
    private Period period;

    @Lob
    @Column()
    private byte[] image;
}
