package com.go_exchange_easier.backend.core.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

@Schema(requiredProperties = {"id", "nick"})
public record UserSummary(

        Integer id,
        String nick

)  implements Serializable { }
