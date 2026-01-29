package com.go_exchange_easier.backend.domain.auth;

import com.go_exchange_easier.backend.domain.auth.dto.RegistrationRequest;
import com.go_exchange_easier.backend.domain.auth.dto.RegistrationSummary;

public interface UserRegistrar {

    RegistrationSummary register(RegistrationRequest request);

}
