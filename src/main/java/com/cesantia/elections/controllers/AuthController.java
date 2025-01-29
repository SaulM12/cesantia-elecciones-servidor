package com.cesantia.elections.controllers;

import com.cesantia.elections.dtos.ApiMessage;
import com.cesantia.elections.dtos.LoginUserDto;
import com.cesantia.elections.dtos.NewUserDto;
import com.cesantia.elections.entities.User;
import com.cesantia.elections.services.AuthService;
import com.cesantia.elections.services.CookieService;
import com.cesantia.elections.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private CookieService cookieService;


    @PostMapping("/login")
    public ResponseEntity<ApiMessage> login(@Valid @RequestBody LoginUserDto loginUserDto, BindingResult bindingResult, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ApiMessage("Revise sus credenciales"));
        }
        try {
            String roleName = authService.authenticate(loginUserDto.getUserName(), loginUserDto.getPassword(), response);
            return ResponseEntity.ok(new ApiMessage(roleName));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ApiMessage("Revise sus credenciales"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiMessage> register(@Valid @RequestBody NewUserDto newUserDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(new ApiMessage("Revise sus credenciales"));
        }
        try {
            authService.registerUser(newUserDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(new ApiMessage("Registrado"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new ApiMessage(e.getMessage()));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        // Usar el servicio de cookies para eliminar la cookie JWT
        cookieService.deleteCookie("jwt", response);

        return ResponseEntity.ok(new ApiMessage("Logout exitoso"));
    }

    @GetMapping("/details")
    public ResponseEntity<User> getAuthenticatedUser() {
        User userInfo = userService.getAuthenticatedUserInfo();
        return ResponseEntity.ok(userInfo);
    }
}
