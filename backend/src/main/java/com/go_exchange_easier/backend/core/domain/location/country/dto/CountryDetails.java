package com.go_exchange_easier.backend.core.domain.location.country.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import java.io.Serializable;

@Schema(requiredProperties = {"id", "englishName"})
public record CountryDetails(

        Short id,
        String englishName,
        @Nullable String flagUrl

) implements Serializable { }