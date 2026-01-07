package com.go_exchange_easier.backend.security.jwt;

import com.go_exchange_easier.backend.model.UserCredentials;

public interface JwtTokenGenerator {

    String generateAccessToken(UserCredentials credentials);
    String generateRefreshToken();

}
