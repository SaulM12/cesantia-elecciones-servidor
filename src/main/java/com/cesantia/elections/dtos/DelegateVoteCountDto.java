package com.cesantia.elections.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DelegateVoteCountDto {

    private Long candidateId;
    private String candidateName;
    private Long voteCount;
    private byte[] image;

}

