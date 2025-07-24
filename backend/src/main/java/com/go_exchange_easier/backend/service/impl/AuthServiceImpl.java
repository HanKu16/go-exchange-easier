package com.go_exchange_easier.backend.service.impl;

import com.go_exchange_easier.backend.dto.auth.LoginRequest;
import com.go_exchange_easier.backend.dto.auth.LoginResponse;
import com.go_exchange_easier.backend.model.UserCredentials;
import com.go_exchange_easier.backend.repository.UserCredentialsRepository;
import com.go_exchange_easier.backend.service.AuthService;
import com.go_exchange_easier.backend.service.JwtTokenGenerator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserCredentialsRepository userCredentialsRepository;
    private final JwtTokenGenerator jwtTokenGenerator;

    @Override
    public LoginResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.login(), request.password()));
        UserCredentials credentials = userCredentialsRepository
                .findByUsername(request.login()).get();
        String token = jwtTokenGenerator.generate(credentials);
        String tokenType = "Bearer";
        return new LoginResponse(credentials.getId(), token, tokenType);
    }

}
