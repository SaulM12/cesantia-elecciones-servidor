package com.cesantia.elections.controllers;

import com.cesantia.elections.entities.User;
import com.cesantia.elections.services.UnitService;
import com.cesantia.elections.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAuthenticatedUser() {
        return ResponseEntity.ok(userService.findAll());
    }
}
