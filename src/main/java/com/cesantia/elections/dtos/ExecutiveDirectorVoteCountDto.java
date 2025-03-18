package com.cesantia.elections.dtos;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ExecutiveDirectorVoteCountDto {
    private Long id;
    private String nomineeCi;
    private String grade;
    private String nomineeName;
    private String electionName;
    private Long voteCount;
    private byte[] image;
}
