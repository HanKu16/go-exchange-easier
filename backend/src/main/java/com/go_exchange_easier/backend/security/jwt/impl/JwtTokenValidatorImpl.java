package com.go_exchange_easier.backend.security.jwt.impl;

import com.go_exchange_easier.backend.security.jwt.JwtTokenValidator;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import io.jsonwebtoken.security.SignatureException;

/**
 * {@code JwtTokenValidatorImpl} is a class responsible for validating
 * JWT token. It checks if token was not malformed, expired and other
 * issues with token. Class logs if something was wrong with the token.
 */
@Component
@RequiredArgsConstructor
public class JwtTokenValidatorImpl implements JwtTokenValidator {

    private static final Logger logger = LogManager.getLogger(
            JwtTokenValidatorImpl.class);
    private final SecretKey signingKey;

    @Override
    public boolean validate(String token) {
        try {
            tryValidate(token);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token format: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty or malformed: {}", e.getMessage());
        } catch (JwtException e) {
            logger.error("An unexpected JWT error occurred: {}", e.getMessage());
        }
        return false;
    }

    private void tryValidate(String token) {
        Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token);
    }

}
