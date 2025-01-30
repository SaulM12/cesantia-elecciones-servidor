package com.cesantia.elections.dtos;

import com.cesantia.elections.entities.Role;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewUserDto {
    public String ci;
    public String password;
    public Role role;
}
