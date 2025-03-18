package com.cesantia.elections.dtos;

import java.util.List;

public class QuadrantFileDto {
    private String description;
    private List<ElectionTypeDto> electionTypes;

    public QuadrantFileDto(String description, List<ElectionTypeDto> electionTypes) {
        this.description = description;
        this.electionTypes = electionTypes;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ElectionTypeDto> getElectionTypes() {
        return electionTypes;
    }

    public void setElectionTypes(List<ElectionTypeDto> electionTypes) {
        this.electionTypes = electionTypes;
    }
}
