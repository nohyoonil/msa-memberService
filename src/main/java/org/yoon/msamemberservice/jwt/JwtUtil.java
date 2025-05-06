package org.yoon.msamemberservice.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;

@Component
public class JwtUtil {


    @Value("${jwt.issuer}")
    private String issuer;
    @Value("${jwt.secret}")
    private String secret;
    private SecretKey key;
    private final static String TOKEN_PREFIX = "Bearer ";

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(Long id, String email, Duration expiration) {
        final Date now = new Date();

        return Jwts.builder()
                .setIssuer(issuer)
                .claim("id", id)
                .claim("email", email)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiration.toMillis())) // 1시간
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
}
