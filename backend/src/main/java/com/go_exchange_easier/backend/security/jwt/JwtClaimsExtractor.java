package com.go_exchange_easier.backend.security.jwt;

import java.util.Date;
import java.util.List;

public interface JwtClaimsExtractor {

    int extractUserId(String token);
    String extractUsername(String token);
    List<String> extractRoles(String token);
    Date extractExpirationDate(String token);

}
