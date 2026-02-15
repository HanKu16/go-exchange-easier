package com.go_exchange_easier.backend.core.domain.exchange.impl;

import com.go_exchange_easier.backend.core.domain.exchange.Exchange;
import com.go_exchange_easier.backend.core.domain.exchange.dto.ExchangeDetails;
import com.go_exchange_easier.backend.core.domain.exchange.dto.TimeRangeSummary;
import com.go_exchange_easier.backend.core.domain.fieldofstudy.FieldOfStudySummary;
import com.go_exchange_easier.backend.core.domain.university.impl.UniversityMapper;
import com.go_exchange_easier.backend.core.domain.user.dto.UserSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExchangeMapper {

    private final UniversityMapper universityMapper;

    public ExchangeDetails toDetails(Exchange exchange) {
        if (exchange == null) {
            return null;
        }
        return new ExchangeDetails(
                exchange.getId(),
                new TimeRangeSummary(exchange.getStartedAt(),
                        exchange.getEndAt()),
                UserSummary.fromEntity(exchange.getUser()),
                universityMapper.toDetails(exchange.getUniversity()),
                FieldOfStudySummary.fromEntity(exchange.getFieldOfStudy())
        );
    }

}
