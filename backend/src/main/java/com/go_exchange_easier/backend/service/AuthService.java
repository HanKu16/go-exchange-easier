package com.go_exchange_easier.backend.service;

import com.go_exchange_easier.backend.dto.auth.LoginRequest;
import com.go_exchange_easier.backend.dto.auth.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);

}
