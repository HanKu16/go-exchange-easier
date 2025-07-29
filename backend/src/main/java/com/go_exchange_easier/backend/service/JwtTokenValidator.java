package com.go_exchange_easier.backend.service;

public interface JwtTokenValidator {

    boolean validate(String token);

}
