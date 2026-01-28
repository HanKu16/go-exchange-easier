package com.go_exchange_easier.backend.infrastracture.security.jwt;

public interface JwtTokenValidator {

    boolean validate(String token);

}
