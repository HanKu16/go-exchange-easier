package com.go_exchange_easier.backend.core.domain.exchange.dto;

import com.go_exchange_easier.backend.core.domain.fieldofstudy.FieldOfStudySummary;
import com.go_exchange_easier.backend.core.domain.university.dto.UniversityDetails;
import com.go_exchange_easier.backend.core.domain.user.dto.UserSummary;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;

@Schema(requiredProperties = {"id", "timeRange", "user",
        "university", "fieldOfStudy"})
public record ExchangeDetails(

        Integer id,
        TimeRangeSummary timeRange,
        UserSummary user,
        UniversityDetails university,
        FieldOfStudySummary fieldOfStudy

) implements Serializable { }



