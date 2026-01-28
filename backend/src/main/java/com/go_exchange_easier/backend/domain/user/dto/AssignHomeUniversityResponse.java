package com.go_exchange_easier.backend.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response body for successful assigning home university to user")
public record AssignHomeUniversityResponse(

        Integer userId,
        Short universityId,
        String originalUniversityName,
        String englishUniversityName

) { }
