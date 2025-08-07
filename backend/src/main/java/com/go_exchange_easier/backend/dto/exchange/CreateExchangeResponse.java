package com.go_exchange_easier.backend.dto.exchange;

import java.time.LocalDate;

public record CreateExchangeResponse(

        Integer id,
        LocalDate startedAt,
        LocalDate endAt,
        Integer userId,
        Short universityId,
        Short universityMajorId

) { }
