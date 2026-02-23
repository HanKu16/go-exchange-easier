package com.go_exchange_easier.backend.core.domain.fieldofstudy;

import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

@Schema(requiredProperties = {"id", "name"})
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
