package com.go_exchange_easier.backend.service.impl;

import com.go_exchange_easier.backend.dto.auth.LoginRequest;
import com.go_exchange_easier.backend.dto.auth.LoginResponse;
import com.go_exchange_easier.backend.exception.InvalidPrincipalTypeException;
import com.go_exchange_easier.backend.model.UserCredentials;
import com.go_exchange_easier.backend.service.AuthService;
import com.go_exchange_easier.backend.security.jwt.JwtTokenGenerator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenGenerator jwtTokenGenerator;

    @Override
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.login(), request.password()));
        if (authentication.getPrincipal() instanceof UserCredentials credentials) {
            String token = jwtTokenGenerator.generate(credentials);
            String tokenType = "Bearer";
            return new LoginResponse(credentials.getUser().getId(), token, tokenType);
        }
        throw new InvalidPrincipalTypeException("Principal was expected to be of " +
                "type UserCredentials but was not.");
    }

}
