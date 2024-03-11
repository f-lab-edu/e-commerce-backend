package com.kitchen.creation.commerce.auth.util;

import com.kitchen.creation.commerce.auth.AuthRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final Environment env;
    private SecretKey secretKey;

    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(
                Objects.requireNonNull(env.getProperty("spring.jwt.secret-key"))
                        .getBytes(StandardCharsets.UTF_8)
        );
    }

    public String generateRefreshToken(String userId, AuthRole role) {
        long refreshPeriod = 1000L * 60L * 60L * 24L * 14; // 2 weeks

        // set subject and role
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("role", role.toString());

        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshPeriod))
                .signWith(secretKey)
                .compact();
    }


    public String generateAccessToken(String userId, AuthRole role) {
        long tokenPeriod = 1000L * 60L * 30L; // 30 minutes
        Claims claims = Jwts.claims().setSubject(userId);
        claims.put("role", role.toString());

        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + tokenPeriod))
                .signWith(secretKey)
                .compact();

    }


    public boolean verifyToken(String token) {
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);

        return claims.getBody()
                .getExpiration()
                .after(new Date());  // check if the token has expired
    }

    public String getUid(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public AuthRole getRole(String token) {
        return AuthRole.valueOf(
                Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token).getBody().get("role", String.class)
        );
    }

    public boolean hasAdminRole(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token).getBody().get("role", String.class)
                .equals(AuthRole.ADMIN.toString());
    }
}
