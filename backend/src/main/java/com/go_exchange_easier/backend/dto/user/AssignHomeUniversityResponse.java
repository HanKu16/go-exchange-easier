package com.go_exchange_easier.backend.dto.user;

public record AssignHomeUniversityResponse(

        Integer userId,
        Short university,
        String originalUniversityName,
        String englishUniversityName

) { }
