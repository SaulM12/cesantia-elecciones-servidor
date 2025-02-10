package com.cesantia.elections.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@Table(
        uniqueConstraints = {@UniqueConstraint(columnNames = {"delegate_id", "period_id"})})
@NoArgsConstructor
@AllArgsConstructor
public class ExecutiveDirectorVote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "nominee_id", nullable = false)
    private ExecutiveDirectorNominee nominee;

    @ManyToOne
    @JoinColumn(name = "delegate_id", nullable = false)
    private Delegate delegate;

    @ManyToOne
    @JoinColumn(name = "period_id")
    private Period period;

    @Column(name = "user_ip", nullable = false)
    private String userIp;

    @Column(nullable = false)
    private LocalDateTime voteDate;

    @Column(nullable = false)
    private String voteControl;
}
