package com.cts.DoctorAvailablityManagement.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JWTService {

    @Value("${jwt.secret}")
    private String secretKeyString;

    private SecretKey key;

    @PostConstruct
    public void init() {
//        byte[] keyBytes = Decoders.BASE64.decode(secretKeyString);
        key = Keys.hmacShaKeyFor(secretKeyString.getBytes());
    }

    // ✅ Validate the JWT signature + expiration
    public boolean validateToken(String token) {
        try {
            Jwts.parser()        // <-- now it's parser(), not parserBuilder()
                .verifyWith(key)  // new style for verifying signature
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            System.out.println("Invalid JWT: " + e.getMessage());
            return false;
        }
    }

    // ✅ Extract username (subject)
    public String extractUserName(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.getSubject();
    }
}
