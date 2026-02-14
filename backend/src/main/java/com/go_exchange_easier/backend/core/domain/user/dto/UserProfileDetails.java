package com.go_exchange_easier.backend.core.domain.user.dto;

import com.go_exchange_easier.backend.core.domain.location.country.CountryDetails;
import com.go_exchange_easier.backend.core.domain.university.dto.UniversitySummary;
import com.go_exchange_easier.backend.core.domain.user.status.UserStatusSummary;

public record UserProfileDetails(

        Integer userId,
        String nick,
        String avatarUrl,
        String description,
        Boolean isFollowed,
        UniversitySummary homeUniversity,
        CountryDetails countryOfOrigin,
        UserStatusSummary status

) { }
