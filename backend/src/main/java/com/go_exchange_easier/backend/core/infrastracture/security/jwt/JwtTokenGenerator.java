package com.go_exchange_easier.backend.core.infrastracture.security.jwt;

import com.go_exchange_easier.backend.core.domain.auth.entity.Role;
import com.go_exchange_easier.backend.core.infrastracture.security.config.JwtConfig;
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

    public String generateAccessToken(int userId, String username,
            String nick, String avatarKey, Set<Role> roles) {
        Map<String, Object> claims = getClaims(userId,
                username, nick, avatarKey, roles);
        TokenLifetime tokenLifetime = getAccessTokenLifetime();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(String.valueOf(userId))
                .issuedAt(tokenLifetime.issuedAt)
                .expiration(tokenLifetime.expirationAt)
                .and()
                .signWith(jwtConfig.signingKey())
                .compact();
    }

    public String generateRefreshToken() {
        return UUID.randomUUID().toString();
    }

    private Map<String, Object> getClaims(int userId, String username,
            String nick, String avatarKey, Set<Role> roles) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("username", username);
        claims.put("nick", nick);
        claims.put("avatarKey", avatarKey);
        Set<String> roleNames = roles
                .stream()
                .map(Enum::toString)
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
