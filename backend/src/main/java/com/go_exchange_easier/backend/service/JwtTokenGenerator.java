package com.go_exchange_easier.backend.service;

import com.go_exchange_easier.backend.model.UserCredentials;

public interface JwtTokenGenerator {

    String generate(UserCredentials credentials);

}
