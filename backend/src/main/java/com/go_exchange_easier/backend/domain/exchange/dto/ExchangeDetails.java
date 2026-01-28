package com.go_exchange_easier.backend.domain.exchange.dto;

import com.go_exchange_easier.backend.domain.university.dto.UniversityDetails;
import com.go_exchange_easier.backend.domain.fieldofstudy.dto.UniversityMajorDetails;
import com.go_exchange_easier.backend.domain.user.dto.UserSummary;
import com.go_exchange_easier.backend.domain.exchange.Exchange;

public record ExchangeDetails(

        Integer id,
        TimeRangeSummary timeRange,
        UserSummary user,
        UniversityDetails university,
        UniversityMajorDetails major

) {

    public static ExchangeDetails fromEntity(Exchange e) {
        return new ExchangeDetails(
                e.getId(),
                new TimeRangeSummary(e.getStartedAt(), e.getEndAt()),
                UserSummary.fromEntity(e.getUser()),
                UniversityDetails.fromEntity(e.getUniversity()),
                UniversityMajorDetails.fromEntity(e.getUniversityMajor())
        );
    }

}



