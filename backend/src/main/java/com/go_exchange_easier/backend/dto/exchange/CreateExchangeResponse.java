package com.go_exchange_easier.backend.dto.exchange;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Response body for create exchange")
public record CreateExchangeResponse(

        Integer id,
        LocalDate startedAt,
        LocalDate endAt,
        Integer userId,
        Short universityId,
        Short universityMajorId

) { }
