package com.go_exchange_easier.backend.dto.user;

import java.time.OffsetDateTime;

public record UserRegistrationResponse(

        Integer userId,
        String login,
        String nick,
        OffsetDateTime createdAt

) { }
