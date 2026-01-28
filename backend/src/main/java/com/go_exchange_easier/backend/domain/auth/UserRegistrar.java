package com.go_exchange_easier.backend.domain.auth;

import com.go_exchange_easier.backend.domain.auth.dto.UserRegistrationRequest;
import com.go_exchange_easier.backend.domain.auth.dto.UserRegistrationResponse;

public interface UserRegistrar {

    UserRegistrationResponse register(UserRegistrationRequest request);

}
