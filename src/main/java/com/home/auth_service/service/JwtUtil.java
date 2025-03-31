package com.home.auth_service.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class JwtUtil {
    private static final String SECRET_KEY = System.getenv("SECURITY_SECRET");
    private static final Long EXPIRATION_TIME =
            System.getenv("EXPIRATION_TIME") != null
                    ? Long.parseLong(System.getenv("EXPIRATION_TIME")) : 3600L;

    public static String generateToken(String username, Map<String, Object> claims) {
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(Date.from(Instant.now()))
                .expiration(Date.from(Instant.now().plusSeconds(EXPIRATION_TIME)))
                .signWith(getSigningKey())
                .compact();
    }

    public static String extractUsername(String token) {
        if (token == null || token.isEmpty()) {
            log.warn("Username don't extract. Token is null or empty");
            return null;
        }
        return Objects.requireNonNull(extractClaims(token)).getSubject();
    }

    public static Claims extractClaims(String token) {
        if (token == null || token.isEmpty()) {
            log.warn("token is null or empty");
            return null;
        }
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public static boolean isTokenExpired(String token) {
        if (token == null) {
            log.warn("Token for lifetime check is null");
            return true;
        }
        return Objects.requireNonNull(extractClaims(token)).getExpiration().before(new Date());
    }

    private static SecretKey getSigningKey() {
        byte[] encodeKey = Base64.getDecoder().decode(SECRET_KEY);
        if (encodeKey == null) {
            log.error("secret key is null");
            throw new SecurityException("secret key is null");
        }
        return Keys.hmacShaKeyFor(encodeKey);
    }
}
