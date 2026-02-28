package br.com.tiagoschermack.family_tree.shared.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.util.Date;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-ms}")
    private long expirationMs;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(UTF_8));
    }

    public String generateToken(UserAuthenticated userAuthenticated) {
        Date now        = new Date();
        Date expiration = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .subject   (userAuthenticated.getUsername())
                .claim     ("userId", userAuthenticated.getUser().getId())
                .issuedAt  (now)
                .expiration(expiration)
                .signWith  (getSigningKey())
                .compact   ();
    }

    public String extractEmail(String token) {
        return extractClaims(token).getSubject();
    }

    public String extractUserId(String token) {
        return extractClaims(token).get("userId", String.class);
    }

    private Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith       (getSigningKey())
                .build            ()
                .parseSignedClaims(token)
                .getPayload       ();
    }

    public boolean isTokenValid(String token) {
        try {
            extractClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
