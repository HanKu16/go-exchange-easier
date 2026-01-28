package com.go_exchange_easier.backend.domain.auth;

import com.go_exchange_easier.backend.domain.auth.dto.LoginRequest;
import com.go_exchange_easier.backend.domain.auth.dto.TokenBundle;
import com.go_exchange_easier.backend.domain.auth.dto.LoginSummary;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

    LoginSummary login(LoginRequest request, HttpServletRequest servletRequest);
    TokenBundle refresh(String refreshToken, HttpServletRequest servletRequest);
    TokenBundle logout(String refreshToken);

}
