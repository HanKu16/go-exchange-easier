package com.go_exchange_easier.backend.core.api;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(requiredProperties = {"originalUrl", "thumbnailUrl"})
public record CoreAvatar(

        String originalUrl,
        String thumbnailUrl

) { }
