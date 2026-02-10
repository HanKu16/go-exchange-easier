package com.go_exchange_easier.backend.core.domain.auth;

import com.go_exchange_easier.backend.core.domain.auth.dto.RegistrationRequest;
import com.go_exchange_easier.backend.core.domain.auth.dto.RegistrationSummary;

public interface UserRegistrar {

    RegistrationSummary register(RegistrationRequest request);

}
