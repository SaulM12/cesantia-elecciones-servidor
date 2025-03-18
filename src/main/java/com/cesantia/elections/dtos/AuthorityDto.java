package com.cesantia.elections.dtos;

import lombok.Data;

@Data
public class AuthorityDto {
    private String role;
    private String fullName;
    private String grade;
    private String ci;
    private String phone;

    public AuthorityDto(String role, String fullName, String grade, String ci, String phone) {
        this.role = role;
        this.fullName = fullName;
        this.grade = grade;
        this.ci = ci;
        this.phone = phone;
    }
}
