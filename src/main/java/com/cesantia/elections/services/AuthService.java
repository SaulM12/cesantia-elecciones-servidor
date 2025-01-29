package com.cesantia.elections.services;

import com.cesantia.elections.entities.Role;
import com.cesantia.elections.entities.User;
import com.cesantia.elections.enums.RoleList;
import com.cesantia.elections.repositories.RoleRepository;
import com.cesantia.elections.dtos.NewUserDto;
import com.cesantia.elections.jwt.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final CookieService cookieService;

    @Autowired
    public AuthService(UserService userService, RoleRepository roleRepository, PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil, AuthenticationManagerBuilder authenticationManagerBuilder,
                       CookieService cookieService) {
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.cookieService = cookieService;
    }

    public String authenticate(String username, String password, HttpServletResponse response) {
        // Autenticar al usuario
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);
        Authentication authResult = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authResult);
        // Generar el JWT
        String jwt = jwtUtil.generateToken(authResult);

        // Configurar la cookie con el JWT
        cookieService.addHttpOnlyCookie("jwt", jwt, 7 * 24 * 60 * 60, response);

        // Obtener el usuario autenticado
        User user = userService.findByCi(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        // Devolver el rol del usuario
        return user.getRole().getName().toString();
    }

    public void registerUser(NewUserDto newUserDto) {
        if (userService.existsByCi(newUserDto.getCi())) {
            throw new IllegalArgumentException("El nombre de usuario ya existe");
        }
        User user = new User(newUserDto.getCi(), passwordEncoder.encode(newUserDto.getPassword()), newUserDto.getRole());
        userService.save(user);
    }
}
