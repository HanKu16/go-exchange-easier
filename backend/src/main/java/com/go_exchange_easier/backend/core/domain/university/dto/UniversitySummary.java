package com.go_exchange_easier.backend.core.domain.university.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import java.io.Serializable;

@Schema(requiredProperties = {"id", "nativeName"})
public record UniversitySummary(

        Short id,
        String nativeName,
        @Nullable String englishName

) implements Serializable { }
