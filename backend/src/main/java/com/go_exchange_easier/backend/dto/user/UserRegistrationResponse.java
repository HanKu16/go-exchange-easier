package com.go_exchange_easier.backend.dto.user;

import java.time.OffsetDateTime;

public record UserRegistrationResponse(int userId, String login,
        String nick, OffsetDateTime createdAt
) {

}
