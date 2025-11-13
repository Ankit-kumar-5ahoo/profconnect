package com.profconnect.profconnect.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    // Generate a long, random secret and move to env in production
    private static final String SECRET = "change_this_super_long_random_secret_key_at_least_64_chars_________";
    private static final long EXP_MS = 1000L * 60 * 60 * 12; // 12h

    private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());

    public String generateToken(String subject, Map<String, Object> claims) {
        long now = System.currentTimeMillis();
        return Jwts.builder()
                .setSubject(subject)
                .addClaims(claims)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + EXP_MS))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Jws<Claims> parse(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }

    public String extractSubject(String token) {
        return parse(token).getBody().getSubject();
    }
}
