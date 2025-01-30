package com.cesantia.elections.dtos;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DelegateVoteCountDto {

    private Long candidateId;
    private String candidateName;
    private Long voteCount;
    private byte[] image;

}

