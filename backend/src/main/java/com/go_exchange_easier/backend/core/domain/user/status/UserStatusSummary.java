package com.go_exchange_easier.backend.core.domain.user.status;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

@Schema(requiredProperties = {"id", "name"})
public record UserStatusSummary(

        Short id,
        String name

) implements Serializable { }
