package com.go_exchange_easier.backend.core.domain.user.dto;

import com.go_exchange_easier.backend.core.domain.location.country.dto.CountryDetails;
import com.go_exchange_easier.backend.core.domain.university.dto.UniversitySummary;
import com.go_exchange_easier.backend.core.domain.user.status.UserStatusSummary;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import java.io.Serializable;

@Schema(requiredProperties = {"userId", "nick", "description"})
public record UserPublicProfile(

        Integer userId,
        String nick,
        @Nullable String avatarUrl,
        String description,
        @Nullable UniversitySummary homeUniversity,
        @Nullable CountryDetails countryOfOrigin,
        @Nullable UserStatusSummary status

) implements Serializable { }
