package com.cesantia.elections.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExecutiveDirectorVoteCountDto {
    private Long id;
    private String nomineeCi;
    private String nomineeName;
    private String electionName;
    private Long voteCount;
    private byte[] image;
}
