package com.go_exchange_easier.backend.service;

import com.go_exchange_easier.backend.dto.auth.UserRegistrationRequest;
import com.go_exchange_easier.backend.dto.auth.UserRegistrationResponse;

public interface UserRegistrar {

    UserRegistrationResponse register(UserRegistrationRequest request);

}
