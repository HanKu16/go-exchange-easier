package com.go_exchange_easier.backend.dto.user;

import java.time.OffsetDateTime;

public record UpdateUserDescriptionResponse(

        Integer userId,
        String description,
        OffsetDateTime updatedAt

) { }
