package com.go_exchange_easier.backend.core.api;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import java.io.Serializable;
import java.util.UUID;

@Schema(requiredProperties = {"nick"})
public record CoreUser(

        @Nullable
        UUID id,

        String nick,

        @Nullable
        CoreAvatar avatar

) implements Serializable {

    public static final CoreUser UNKNOWN = new CoreUser(null, "Unknown user", null);

}
