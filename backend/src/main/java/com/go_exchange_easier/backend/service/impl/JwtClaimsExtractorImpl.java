package com.go_exchange_easier.backend.service.impl;

import com.go_exchange_easier.backend.service.JwtClaimsExtractor;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.function.Function;
import java.util.List;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtClaimsExtractorImpl implements JwtClaimsExtractor {

    private final SecretKey signingKey;

    @Override
    public int extractUserId(String token) {
        return extractClaim(token, claims -> (int) claims.get("userId"));
    }

    @Override
    public String extractUsername(String token) {
        return extractClaim(token, claims -> (String) claims.get("username"));
    }

    @Override
    public List<String> extractRoles(String token) {
        return extractClaim(token, claims -> (List<String>) claims.get("roles"));
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
