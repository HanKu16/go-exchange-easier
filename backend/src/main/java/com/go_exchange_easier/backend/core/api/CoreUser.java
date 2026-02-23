package com.go_exchange_easier.backend.core.api;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;

@Schema(requiredProperties = {"avatar"})
public record CoreUser(

        @Nullable Integer id,
        String nick,
        @Nullable CoreAvatar avatar

) {

    public static final CoreUser UNKNOWN = new CoreUser(
            null,
            "Unknown user",
            null
    );

}
