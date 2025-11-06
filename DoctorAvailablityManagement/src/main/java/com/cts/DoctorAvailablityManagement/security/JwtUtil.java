package com.cts.DoctorAvailablityManagement.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
 
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
	
	@Value("${jwt.secret}")
    private String secret;
 
    private Key getSignKey() {
    	byte[] keyBytes = Decoders.BASE64.decode(Base64.getEncoder().encodeToString(secret.getBytes()));
        return Keys.hmacShaKeyFor(keyBytes);
    }
 
    private Claims extractAllClaims(String token) {
    	Claims claims= Jwts.parserBuilder()  //creates a parser that can read and validate JWT tokens. 
                .setSigningKey(getSignKey())  // it contain only signature not the secret key so we verify the signature with the same key
                .build()
                .parseClaimsJws(token) // Parses the JWT string (token) as a signed JWT (JWS).
                .getBody(); //Extracts the claims (payload) from the token(sub,userid,..)
        
        return claims;
    }
 
    public String extractUserEmail(String token) {
        return extractAllClaims(token).getSubject(); // Which extract the subject which is the user email
    }
 
    public int extractUserId(String token) {
        return extractAllClaims(token).get("userId", int.class);  // map<String,object> => userId is the key and the int.class(value of the key) will get the userid and return it as int.
    }
 
    public String extractUserRole(String token) {
        return extractAllClaims(token).get("role", String.class);
    }
 
    public boolean isTokenExpired(String token) {
        Date expiration = extractAllClaims(token).getExpiration();
        return expiration.before(new Date()); // It check whether the token is expired or not
    }
 
    public boolean isTokenValid(String token) {
        try {
            return !isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }
}
