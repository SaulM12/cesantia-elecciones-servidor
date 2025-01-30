package com.cesantia.elections.entities;

import com.cesantia.elections.enums.InvitationStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chair_number", nullable = false)
    private Integer chairNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "table_entity_id", nullable = false)
    private TableEntity tableEntity;

    @OneToOne
    @JoinColumn(name = "delegate_id")
    private Delegate delegate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private InvitationStatus status;

    @Column
    private Boolean scanned = false;


}
