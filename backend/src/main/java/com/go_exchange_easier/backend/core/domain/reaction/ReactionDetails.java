package com.go_exchange_easier.backend.core.domain.reaction;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

@Schema(requiredProperties = {"type", "count", "isSet"})
public record ReactionDetails(

        ReactionType type,
        Short count,
        Boolean isSet

) implements Serializable { }
