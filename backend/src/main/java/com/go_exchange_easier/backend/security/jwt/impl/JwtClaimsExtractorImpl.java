package com.go_exchange_easier.backend.security.jwt.impl;

import com.go_exchange_easier.backend.exception.MissingJwtClaimException;
import com.go_exchange_easier.backend.security.jwt.JwtClaimsExtractor;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.function.Function;
import java.util.List;
import java.util.Date;

/**
 * {@code JwtClaimsExtractorImpl} is a utility class responsible for extracting
 * various claims from a JSON Web Token (JWT).
 * If there is no claim in of given name in the token, it
 * throws MissingJwtClaimException.
 */
@Component
@RequiredArgsConstructor
public class JwtClaimsExtractorImpl implements JwtClaimsExtractor {

    private final SecretKey signingKey;

    @Override
    public int extractUserId(String token) {
        Integer userId = extractClaim(token,
                claims -> (Integer) claims.get("userId"));
        if (userId == null) {
            throw new MissingJwtClaimException("There is " +
                    "no claim 'userId' in the token.");
        }
        return userId;
    }

    @Override
    public String extractUsername(String token) {
        String username = extractClaim(token,
                claims -> (String) claims.get("username"));
        if (username == null) {
            throw new MissingJwtClaimException("There is " +
                    "no claim 'username' in the token.");
        }
        return username;
    }

    @Override
    public List<String> extractRoles(String token) {
        List<String> roles = extractClaim(token,
                claims -> (List<String>) claims.get("roles"));
        if (roles == null) {
            throw new MissingJwtClaimException("There is " +
                    "no claim 'roles' in the token.");
        }
        return roles;
    }

    @Override
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
