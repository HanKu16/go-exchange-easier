package com.go_exchange_easier.backend.core.api;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import java.io.Serializable;

@Schema(requiredProperties = {"avatar"})
public record CoreUser(

        @Nullable
        Integer id,

        String nick,

        @Nullable
        CoreAvatar avatar

) implements Serializable {

    public static final CoreUser UNKNOWN = new CoreUser(null, "Unknown user", null);

}
