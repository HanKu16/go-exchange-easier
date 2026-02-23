package com.go_exchange_easier.backend.core.domain.university.dto;

import com.go_exchange_easier.backend.core.domain.location.city.CityDetails;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import java.io.Serializable;

@Schema(requiredProperties = {"id", "nativeName", "englishName", "city"})
public record UniversityDetails(

        Short id,
        String nativeName,
        @Nullable String englishName,
        CityDetails city

) implements Serializable { }
