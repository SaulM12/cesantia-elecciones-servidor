package com.cesantia.elections.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Delegate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column( nullable = false, unique = true, length = 10)
    private String ci;
    @Column(nullable = false)
    private String phone;
    @Column
    private String ingresoAsis;
    @Column
    private String dependence;
    @Column
    private String unitName;
    @Column(nullable = false)
    private String names;
    @Column(name = "email")
    private String email;
    @Column
    private Boolean candidate = false;
    @Column
    private Boolean confirmation;
    @Column
    private Boolean active = true;
    @Column
    private Boolean enableToVote = false;
    @ManyToOne
    @JoinColumn(name = "grade_id", nullable = false)
    private Grade grade;
    @ManyToOne
    @JoinColumn(name = "delegate_type_id")
    private DelegateType delegateType;

}
