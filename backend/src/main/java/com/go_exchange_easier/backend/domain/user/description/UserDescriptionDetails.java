package com.go_exchange_easier.backend.domain.user.description;

import java.time.OffsetDateTime;

public record UserDescriptionDetails(

        Integer userId,
        String description,
        OffsetDateTime updatedAt

) { }
