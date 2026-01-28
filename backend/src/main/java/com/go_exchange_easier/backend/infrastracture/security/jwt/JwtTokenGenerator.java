package com.go_exchange_easier.backend.infrastracture.security.jwt;

import com.go_exchange_easier.backend.domain.auth.UserCredentials;

public interface JwtTokenGenerator {

    String generateAccessToken(UserCredentials credentials);
    String generateRefreshToken();

}
