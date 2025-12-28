package com.example.demo.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    
    @Value("${jwt.secret:super-secret-key-that-is-at-least-32-characters-long}")
    private String jwtSecret;
    
    @Value("${jwt.expiration:86400000}")
    private long jwtExpirationMs;
    
    private Key key;
    
    @PostConstruct
    public void init() {
        // Ensure the secret is at least 256 bits (32 characters)
        if (jwtSecret.length() < 32) {
            // Pad with default characters to reach 32 characters
            StringBuilder paddedSecret = new StringBuilder(jwtSecret);
            while (paddedSecret.length() < 32) {
                paddedSecret.append("0");
            }
            jwtSecret = paddedSecret.toString();
        }
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }
    
    public String generateToken(Long userId, String email, Set<String> roles) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + jwtExpirationMs);
        
        String rolesString = String.join(",", roles);
        
        return Jwts.builder()
                .setSubject(email)
                .claim("userId", userId)
                .claim("email", email)
                .claim("roles", rolesString)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }
    
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
    
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        String email = claims.getSubject();
        String rolesString = (String) claims.get("roles");
        List<GrantedAuthority> authorities = Arrays.stream(rolesString.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        
        return new UsernamePasswordAuthenticationToken(email, null, authorities);
    }
    
    public String getEmailFromToken(String token) {
        return getClaims(token).getSubject();
    }
    
    public Long getUserIdFromToken(String token) {
        Object userIdObj = getClaims(token).get("userId");
        if (userIdObj instanceof Integer) {
            return ((Integer) userIdObj).longValue();
        }
        return Long.valueOf(userIdObj.toString());
    }
}