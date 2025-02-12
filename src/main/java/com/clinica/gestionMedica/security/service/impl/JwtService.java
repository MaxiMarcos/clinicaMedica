package com.clinica.gestionMedica.security.service.impl;

import com.clinica.gestionMedica.security.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    @Value("${SECURITY_SECRET}")
    private String secretKey;

    @Value("${api.security.jwt.expiration}")
    private long jwtExpiration;

    @Value("${api.security.jwt.refresh-token-expiration}")
    private long refreshExpiration;

    public String extractUsername(final String token){
        final Claims jwtToken = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return jwtToken.getSubject();
    }

    public String generateToken(final User user){
        return buildToken(user, jwtExpiration);
    }

    public String generateRefreshToken(final User user){
        return buildToken(user, refreshExpiration);
    }

    private String buildToken(final User user, final long expiration){
        return Jwts.builder()
                .id(user.getId().toString())
                .claims(Map.of("name", user.getUsername()))
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey())
                .compact();
    }

    public boolean isTokenValid(final String token, final User user){
        final String username = extractUsername(token);
        return (username.equals(user.getEmail())) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(final String token){
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(final String token){
        final Claims jwtToken = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return jwtToken.getExpiration();
    }

    private SecretKey getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
