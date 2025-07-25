package com.go_exchange_easier.backend.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtTokenValidator {

    boolean validate(String token, UserDetails userDetails);

}
