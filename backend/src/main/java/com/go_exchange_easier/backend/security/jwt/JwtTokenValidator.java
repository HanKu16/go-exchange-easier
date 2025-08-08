package com.go_exchange_easier.backend.security.jwt;

public interface JwtTokenValidator {

    boolean validate(String token);

}
