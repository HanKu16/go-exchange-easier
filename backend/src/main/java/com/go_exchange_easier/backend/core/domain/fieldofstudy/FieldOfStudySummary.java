package com.go_exchange_easier.backend.core.domain.fieldofstudy;

import java.io.Serializable;

public record FieldOfStudySummary(

        Short id,
        String name

) implements Serializable {

    public static FieldOfStudySummary fromEntity(FieldOfStudy u) {
        return new FieldOfStudySummary(
                u.getId(),
                u.getName()
        );
    }

}
