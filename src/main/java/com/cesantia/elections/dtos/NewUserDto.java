package com.cesantia.elections.dtos;

import com.cesantia.elections.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUserDto {
    public String ci;
    public String password;
    public Role role;
}
