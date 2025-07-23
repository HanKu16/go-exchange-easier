package com.go_exchange_easier.backend.service;

import com.go_exchange_easier.backend.dto.user.UserRegistrationRequest;
import com.go_exchange_easier.backend.dto.user.UserRegistrationResponse;

public interface UserRegistrar {

    UserRegistrationResponse register(UserRegistrationRequest request);

}
