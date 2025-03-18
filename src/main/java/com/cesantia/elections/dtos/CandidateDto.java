package com.cesantia.elections.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CandidateDto {
    private String cedula;
    private String grado;
    private String nombre;

    private int votos;


}
