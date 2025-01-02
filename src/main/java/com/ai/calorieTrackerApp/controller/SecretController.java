package com.ai.calorieTrackerApp.controller;



import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/secret")
@CrossOrigin(origins = "http://localhost:4200")
public class SecretController {

    @GetMapping("/")
    public ResponseEntity<String> getSecret(){
        return  ResponseEntity
                .status(HttpStatus.OK)
                .body(UUID.randomUUID().toString());
    }
}
