package com.go_exchange_easier.backend.dto.common;

import java.util.List;

public record Listing<T>(

        List<T> content

) {

    public static <T> Listing<T> of(List<T> content) {
        return new Listing<>(content);
    }

}
