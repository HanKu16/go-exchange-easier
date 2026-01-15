package com.go_exchange_easier.backend.service;

import com.go_exchange_easier.backend.dto.auth.LoginRequest;
import com.go_exchange_easier.backend.dto.auth.TokenBundle;
import com.go_exchange_easier.backend.dto.summary.LoginSummary;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthService {

    LoginSummary login(LoginRequest request, HttpServletRequest servletRequest);
    TokenBundle refresh(String refreshToken, HttpServletRequest servletRequest);
    TokenBundle logout(String refreshToken);

}
