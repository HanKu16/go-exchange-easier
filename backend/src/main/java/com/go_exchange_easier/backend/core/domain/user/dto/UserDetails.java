package com.go_exchange_easier.backend.core.domain.user.dto;

import com.go_exchange_easier.backend.core.domain.location.country.dto.CountryDetails;
import com.go_exchange_easier.backend.core.domain.university.dto.UniversityDetails;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import java.io.Serializable;
import java.util.UUID;

@Schema(requiredProperties = {"id", "nick"})
public record UserDetails(
        UUID id,
        String nick,

        @Nullable
        CountryDetails country,

        @Nullable
        UniversityDetails university

) implements Serializable { }
