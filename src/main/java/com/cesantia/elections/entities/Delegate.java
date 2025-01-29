package com.cesantia.elections.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Delegate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( nullable = false, unique = true, length = 10)
    private String ci;

    @Column(nullable = false, unique = true, length = 10)
    private String phone;

    @Column(unique = true, length = 10)
    private String homePhone;

    @Column(nullable = false)
    private String names;

    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String secondLastName;

    @Column(name = "email")
    private String email;

    @Column
    private Boolean candidate;

    @Column
    private Boolean completeInfo;

    @Column
    private Boolean active = true;

    @Column
    private Boolean enableToVote = false;

    @ManyToOne
    @JoinColumn(name = "grade_id", nullable = false)
    private Grade grade;

    @ManyToOne
    @JoinColumn(name = "delegate_type_id", nullable = false)
    private DelegateType delegateType;

    @ManyToOne
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @Lob
    @Column()
    private byte[] image;

}
