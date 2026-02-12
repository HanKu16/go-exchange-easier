package com.go_exchange_easier.backend.common.dto;

import java.util.List;

public record SimplePage<T>(
        List<T> content,
        Integer pageNumber,
        Integer pageSize,
        Long totalElements,
        Integer totalPages
) {

    public static <T> SimplePage<T> of(List<T> content,
            Integer pageNumber, Integer pageSize, Long totalElements) {
        if (pageSize == 0) return empty(0);
        int calculatedTotalPages = (int) Math.ceilDiv(totalElements, pageSize);
        return new SimplePage<>(content, pageNumber, pageSize,
                totalElements, calculatedTotalPages);
    }

    public static <T> SimplePage<T> empty(Integer requestedPageSize) {
        return new SimplePage<>(List.of(), 0, requestedPageSize, 0L, 0);
    }
}