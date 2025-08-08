package com.go_exchange_easier.backend.security.jwt.impl;

import com.go_exchange_easier.backend.model.Role;
import com.go_exchange_easier.backend.model.UserCredentials;
import com.go_exchange_easier.backend.security.jwt.JwtTokenGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.temporal.ChronoUnit;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import io.jsonwebtoken.Jwts;
import javax.crypto.SecretKey;

/**
 * {@code JwtTokenGeneratorImpl} is a class responsible for generating
 * JWT token used to authenticate and authorize user. It puts additional
 * claims like userId, username and roles inside the token. Token is
 * signed by signing key to be able later to check if he was not
 * malformed by somebody.
 */
@Component
@RequiredArgsConstructor
public class JwtTokenGeneratorImpl implements JwtTokenGenerator {

    private final SecretKey signingKey;

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
        Set<String> roleNames = credentials.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
        claims.put("roles", roleNames);
        return claims;
    }

    private TokenLifetime getTokenLifetime() {
        Instant now = Instant.now();
        Instant expiration = now.plus(30, ChronoUnit.MINUTES);
        return new TokenLifetime(Date.from(now), Date.from(expiration));
    }

    private static record TokenLifetime(Date issuedAt, Date expirationAt) { }

}
