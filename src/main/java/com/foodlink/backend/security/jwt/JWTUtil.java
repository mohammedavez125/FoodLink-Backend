package com.foodlink.backend.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JWTUtil {
    public final String SECRET = "mtech-mini-project-Food-Link-secret-auth-Jwt-Signature-key";
    public final SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generateToken(String username){
        //  1 hr
        long EXPIRATION_TIME = 1000 * 60 * 60;
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date()).expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token){
        return extractClaims(token).getSubject();
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validateToken(String username, UserDetails userDetails, String token){return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));}

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }
}
