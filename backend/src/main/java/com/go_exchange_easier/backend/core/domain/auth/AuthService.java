package com.go_exchange_easier.backend.core.domain.auth;

import com.go_exchange_easier.backend.core.domain.auth.dto.LoginRequest;
import com.go_exchange_easier.backend.core.domain.auth.dto.TokenBundle;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

    TokenBundle login(LoginRequest request, HttpServletRequest servletRequest);
    TokenBundle refresh(String refreshToken, HttpServletRequest servletRequest);
    TokenBundle logout(String refreshToken);

}
