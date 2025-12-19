package com.go_exchange_easier.backend.dto.details;

import com.go_exchange_easier.backend.dto.summary.TimeRangeSummary;
import com.go_exchange_easier.backend.dto.summary.UserSummary;
import com.go_exchange_easier.backend.model.Exchange;

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



