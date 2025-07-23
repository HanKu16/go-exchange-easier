package com.go_exchange_easier.backend.dto.auth;

public record LoginResponse(int userId, String accessToken,
                            String tokenType) {

}
