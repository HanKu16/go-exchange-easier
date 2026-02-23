package com.go_exchange_easier.backend.core.domain.user.avatar;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

@Schema(requiredProperties = {"original", "thumbnail"})
public record AvatarUrlSummary(

        String original,
        String thumbnail

) implements Serializable { }
