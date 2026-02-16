package com.go_exchange_easier.backend.core.domain.user.dto;

import com.go_exchange_easier.backend.core.domain.location.country.dto.CountryDetails;
import com.go_exchange_easier.backend.core.domain.university.dto.UniversitySummary;
import com.go_exchange_easier.backend.core.domain.user.status.UserStatusSummary;
import java.io.Serializable;

public record UserPublicProfile(

        Integer userId,
        String nick,
        String avatarUrl,
        String description,
        UniversitySummary homeUniversity,
        CountryDetails countryOfOrigin,
        UserStatusSummary status

) implements Serializable { }
