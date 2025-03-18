package com.cesantia.elections.dtos;

import java.util.List;

public class ActaEleccionDto {
    private List<QuadrantFileDto> quadrants;

    public ActaEleccionDto(List<QuadrantFileDto> quadrants) {
        this.quadrants = quadrants;
    }

    public List<QuadrantFileDto> getQuadrants() {
        return quadrants;
    }

    public void setQuadrants(List<QuadrantFileDto> quadrants) {
        this.quadrants = quadrants;
    }
}
