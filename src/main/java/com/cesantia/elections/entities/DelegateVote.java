package com.cesantia.elections.entities;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "delegate_vote",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"delegate_id", "period_id"})})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DelegateVote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "delegate_id", nullable = false)
    private Delegate delegate;

    @ManyToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    private Delegate candidate;

    @ManyToOne
    @JoinColumn(name = "period_id", nullable = false)
    private Period period;

    @Column(name = "vote_date", nullable = false)
    private LocalDateTime voteDate;

    @Column(name = "vote_control", nullable = false)
    private String voteControl;
}
