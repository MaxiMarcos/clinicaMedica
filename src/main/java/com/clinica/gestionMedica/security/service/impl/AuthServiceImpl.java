package com.clinica.gestionMedica.security.service.impl;


import com.clinica.gestionMedica.entity.Paciente;
import com.clinica.gestionMedica.repository.PacienteRepository;
import com.clinica.gestionMedica.security.config.TokenResponse;
import com.clinica.gestionMedica.security.dto.AuthResponse;
import com.clinica.gestionMedica.security.dto.LoginRequest;
import com.clinica.gestionMedica.security.dto.RegisterRequest;
import com.clinica.gestionMedica.security.dto.UserResponse;
import com.clinica.gestionMedica.security.entity.Token;
import com.clinica.gestionMedica.security.entity.User;
import com.clinica.gestionMedica.security.enumRole.RoleName;
import com.clinica.gestionMedica.security.repository.TokenRepository;
import com.clinica.gestionMedica.security.repository.UserRepository;
import com.clinica.gestionMedica.security.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements IAuthService {

    private final UserRepository authRepo;
    private final TokenRepository tokenRepo;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final PacienteRepository pacienteRepo;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserRepository authRepo, TokenRepository tokenRepo, JwtService jwtService, PasswordEncoder passwordEncoder, PacienteRepository pacienteRepo, AuthenticationManager authenticationManager) {
        this.authRepo = authRepo;
        this.tokenRepo = tokenRepo;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.pacienteRepo = pacienteRepo;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public TokenResponse register(RegisterRequest registerRequest, RoleName roleName){

        User user = User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(roleName)
                .paciente(pacienteRepo.findByEmail(registerRequest.getEmail())) // se asocia con un paciente si existe el mail, sino queda null ya que un usuario puede no ser paciente
                .build();

        var savedUser = authRepo.save(user);
        var jwtToken = jwtService.generateToken(savedUser);
        var refreshToken = jwtService.generateRefreshToken(savedUser);
        saveUserToken(savedUser, jwtToken);


        return new TokenResponse(jwtToken, refreshToken);

    }

    @Override
    public TokenResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        var user = authRepo.findByEmail(loginRequest.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        saveUserToken(user, jwtToken);
        //revokeAllUserTokens(user);
        return new TokenResponse(jwtToken, refreshToken);
    }

    @Override
    public TokenResponse refreshToken(String authHeader) {

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            throw new IllegalArgumentException("Invalid Bearer token");
        }
        final String refreshToken = authHeader.substring(7);
        final String userEmail = jwtService.extractUsername(refreshToken);

        if(userEmail == null){
            throw new IllegalArgumentException("Invalid refresh token");
        }
        final User user = authRepo.findByEmail(userEmail)
                .orElseThrow(()-> new UsernameNotFoundException(userEmail));
        if(!jwtService.isTokenValid(refreshToken, user)){
            throw new IllegalArgumentException("Invalid refresh Token");
        }
        final String accessToken = jwtService.generateToken(user);
        //revokeAllUserTokens(user);
        saveUserToken(user, accessToken);
        return new TokenResponse(accessToken, refreshToken);
    }

    @Override
    public List<UserResponse> findAll() {
        List<User> users = authRepo.findAll();

        return users.stream()
                .map(user -> new UserResponse(user.getUsername(), user.getEmail(), user.getRole()))
                .collect(Collectors.toList());

    }


    private void saveUserToken(User user, String jwtToken){
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(Token.TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();

        tokenRepo.save(token);
    }
}

