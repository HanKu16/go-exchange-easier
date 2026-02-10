package com.go_exchange_easier.backend.core.infrastracture.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.function.Function;
import java.util.List;
import java.util.Date;

/**
 * {@code JwtClaimsExtractor} is a utility class responsible for extracting
 * various claims from a JSON Web Token (JWT).
 * If there is no claim in of given name in the token, it
 * throws MissingJwtClaimException.
 */
@Component
@RequiredArgsConstructor
public class JwtClaimsExtractor {

    private final SecretKey signingKey;

    public int extractUserId(String token) {
        return extractClaim(token, claims -> {
            Object id = claims.get("userId");
            if (id instanceof Integer) return (Integer) id;
            if (id instanceof Long) return ((Long) id).intValue();
            if (id instanceof String) return Integer.parseInt((String) id);
            throw new MissingJwtClaimException("There is " +
                    "no claim 'userId' in the token.");
        });
    }

    public String extractUsername(String token) {
        String username = extractClaim(token,
                claims -> (String) claims.get("username"));
        if (username == null) {
            throw new MissingJwtClaimException("There is " +
                    "no claim 'username' in the token.");
        }
        return username;
    }

    public List<String> extractRoles(String token) {
        return extractClaim(token, claims -> {
            try {
                return claims.get("roles", List.class);
            } catch (Exception e) {
                throw new MissingJwtClaimException("There is " +
                        "no claim 'roles' in the token.");
            }
        });
    }

    public Date extractExpirationDate(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

}
