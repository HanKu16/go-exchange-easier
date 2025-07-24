package com.go_exchange_easier.backend.service.impl;

import com.go_exchange_easier.backend.model.UserCredentials;
import com.go_exchange_easier.backend.service.JwtTokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.temporal.ChronoUnit;
import java.security.Key;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import io.jsonwebtoken.security.Keys;
import java.util.Map;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;

@Service
public class JwtTokenGeneratorImpl implements JwtTokenGenerator {

    private final String secretKey;
    private final Key signingKey;

    @Autowired
    public JwtTokenGeneratorImpl(@Value("${JWT_SECRET_KEY}") String secretKey) {
        this.secretKey = secretKey;
        this.signingKey = generateSigningKey();
    }

    @Override
    public String generate(UserCredentials credentials) {
        Map<String, Object> claims = getClaims(credentials);
        TokenLifetime tokenLifetime = getTokenLifetime();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(String.valueOf(credentials.getId()))
                .issuedAt(tokenLifetime.issuedAt)
                .expiration(tokenLifetime.expirationAt)
                .and()
                .signWith(signingKey)
                .compact();
    }

    private Map<String, Object> getClaims(UserCredentials credentials) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", credentials.getId());
        claims.put("username", credentials.getUsername());
        claims.put("roles", credentials.getRoles());
        return claims;
    }

    private TokenLifetime getTokenLifetime() {
        Instant now = Instant.now();
        Instant expiration = now.plus(30, ChronoUnit.MINUTES);
        return new TokenLifetime(Date.from(now), Date.from(expiration));
    }

    private Key generateSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private static record TokenLifetime(Date issuedAt, Date expirationAt) { }

}
