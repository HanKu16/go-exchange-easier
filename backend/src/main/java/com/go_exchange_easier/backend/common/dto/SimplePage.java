package com.go_exchange_easier.backend.common.dto;

import java.util.List;

public record SimplePage<T>(

        List<T> content,
        Integer pageNumber,
        Integer pageSize,
        Integer totalElements,
        Integer totalPages

) {

    public static <T> SimplePage<T> of(List<T> content,
            Integer pageNumber, Integer pageSize,
            Integer totalElements) {
        return new SimplePage<>(content, pageNumber, pageSize,
                totalElements, Math.ceilDiv(totalElements, pageSize));
    }

    public static <T> SimplePage<T> empty(Integer requestedPageSize) {
        return new SimplePage<>(List.of(), 0, requestedPageSize, 0, 0);
    }

}
