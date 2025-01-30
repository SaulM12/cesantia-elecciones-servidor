package com.cesantia.elections.dtos;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginUserDto {

    public String userName;
    public String password;
}
