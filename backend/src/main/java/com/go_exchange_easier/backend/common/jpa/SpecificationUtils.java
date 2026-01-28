package com.go_exchange_easier.backend.common.jpa;

import org.springframework.data.jpa.domain.Specification;
import java.util.function.Function;

public final class SpecificationUtils {

    private SpecificationUtils() {}

    /**
     * Appends a specification to the current one using AND, but only if the value is not null.
     *
     * @param <E> The entity type (e.g., Exchange, University)
     * @param <V> The value type (e.g., Integer, LocalDate)
     */
    public static <E, V> Specification<E> append(
            Specification<E> current,
            V value,
            Function<V, Specification<E>> specFactory
    ) {
        if (value == null) {
            return current;
        }
        Specification<E> next = specFactory.apply(value);
        return (current == null) ? next : current.and(next);
    }

}