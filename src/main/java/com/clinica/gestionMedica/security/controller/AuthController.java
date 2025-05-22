package com.clinica.gestionMedica.security.controller;


import com.clinica.gestionMedica.entity.Paciente;
import com.clinica.gestionMedica.security.config.TokenResponse;
import com.clinica.gestionMedica.security.dto.AuthResponse;
import com.clinica.gestionMedica.security.dto.LoginRequest;
import com.clinica.gestionMedica.security.dto.RegisterRequest;
import com.clinica.gestionMedica.security.enumRole.RoleName;
import com.clinica.gestionMedica.security.repository.UserRepository;
import com.clinica.gestionMedica.security.service.impl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://127.0.0.1:5500")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthServiceImpl authService;

    @Autowired
    UserRepository userRepo;

    @PostMapping("/register-customer")
    public ResponseEntity<TokenResponse> registerCustomer(@RequestBody RegisterRequest registerRequest) {

        final TokenResponse token = authService.register(registerRequest, RoleName.CUSTOMER);

        return ResponseEntity.ok(token);
    }

    @PostMapping("/register-admin")
    public ResponseEntity<TokenResponse> registerAdmin(@RequestBody RegisterRequest registerRequest) {

        final TokenResponse token = authService.register(registerRequest, RoleName.ADMIN);

        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity authenticate (@RequestBody LoginRequest loginRequest){

        final TokenResponse token = authService.login(loginRequest);

        return ResponseEntity.ok(token);
    }

    @PostMapping("/refresh")
    public TokenResponse refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) String authHeader){

        System.out.println("Auth Header: " + authHeader);
        return authService.refreshToken(authHeader);
    }

}