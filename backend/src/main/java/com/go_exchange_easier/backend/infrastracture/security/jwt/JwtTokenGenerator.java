package com.go_exchange_easier.backend.infrastracture.security.jwt;

import com.go_exchange_easier.backend.domain.auth.entity.Role;
import com.go_exchange_easier.backend.domain.auth.entity.UserCredentials;
import com.go_exchange_easier.backend.infrastracture.security.config.JwtConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.time.temporal.ChronoUnit;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import io.jsonwebtoken.Jwts;

/**
 * {@code JwtTokenGenerator} is a class responsible for generating
 * JWT token used to authenticate and authorize user. It puts additional
 * claims like userId, username and roles inside the token. Token is
 * signed by signing key to be able later to check if he was not
 * malformed by somebody.
 */
@Component
@RequiredArgsConstructor
public class JwtTokenGenerator {

    private final JwtConfig jwtConfig;

    public String generateAccessToken(UserCredentials credentials) {
        Map<String, Object> claims = getClaims(credentials);
        TokenLifetime tokenLifetime = getAccessTokenLifetime();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(String.valueOf(credentials.getId()))
                .issuedAt(tokenLifetime.issuedAt)
                .expiration(tokenLifetime.expirationAt)
                .and()
                .signWith(jwtConfig.signingKey())
                .compact();
    }

    public String generateRefreshToken() {
        return UUID.randomUUID().toString();
    }

    private Map<String, Object> getClaims(UserCredentials credentials) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", credentials.getUser().getId());
        claims.put("username", credentials.getUsername());
        Set<String> roleNames = credentials.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
        claims.put("roles", roleNames);
        return claims;
    }

    private TokenLifetime getAccessTokenLifetime() {
        Instant now = Instant.now();
        Instant expiration = now.plus(jwtConfig.getAccessTokenValidityInSeconds(),
                ChronoUnit.SECONDS);
        return new TokenLifetime(Date.from(now), Date.from(expiration));
    }

    private static record TokenLifetime(Date issuedAt, Date expirationAt) { }

}
