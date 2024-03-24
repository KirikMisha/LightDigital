package com.example.demo.controllers;

import com.example.demo.dtos.JwtRequest;
import com.example.demo.dtos.JwtResponse;
import com.example.demo.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest){
        String token = authService.authenticateAndGetToken(authRequest.getUsername(), authRequest.getPassword());
        return ResponseEntity.ok(new JwtResponse(token));
    }
}

