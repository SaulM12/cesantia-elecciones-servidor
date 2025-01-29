package com.cesantia.elections.controllers;

import com.cesantia.elections.entities.ExecutiveDirectorNominee;
import com.cesantia.elections.services.ExecutiveDirectorNomineeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/nominees")
public class ExecutiveDirectorNomineeController {
    @Autowired
    private ExecutiveDirectorNomineeService nomineeService;

    @GetMapping
    public ResponseEntity<List<ExecutiveDirectorNominee>> getAllNominees() {
        List<ExecutiveDirectorNominee> nominees = nomineeService.getAllNominees();
        return ResponseEntity.ok(nominees);
    }

    @PostMapping
    public ResponseEntity<ExecutiveDirectorNominee> createNominee(@RequestBody ExecutiveDirectorNominee nominee) {
        ExecutiveDirectorNominee createdNominee = nomineeService.createNominee(nominee);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNominee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExecutiveDirectorNominee> updateNominee(@PathVariable Long id, @RequestBody ExecutiveDirectorNominee nomineeDetails) {
        ExecutiveDirectorNominee updatedNominee = nomineeService.updateNominee(id, nomineeDetails);
        return ResponseEntity.ok(updatedNominee);
    }

    @PutMapping("/{id}/image")
    public ResponseEntity<String> uploadNomineeImage(@PathVariable Long id, @RequestParam("image") MultipartFile file) {
        try {
            byte[] imageData = file.getBytes();
            nomineeService.updateNomineeImage(id, imageData);
            return ResponseEntity.ok("Image uploaded successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading image");
        }
    }


}
