package com.go_exchange_easier.backend.service;

import com.go_exchange_easier.backend.dto.auth.LoginRequest;
import com.go_exchange_easier.backend.dto.auth.TokenBundle;

public interface AuthService {

    TokenBundle login(LoginRequest request);

}
