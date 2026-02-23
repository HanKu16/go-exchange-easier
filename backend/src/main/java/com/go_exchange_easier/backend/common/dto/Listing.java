package com.go_exchange_easier.backend.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(requiredProperties = {"content"})
public record Listing<T>(

        List<T> content

) {

    public static <T> Listing<T> of(List<T> content) {
        return new Listing<>(content);
    }

}
