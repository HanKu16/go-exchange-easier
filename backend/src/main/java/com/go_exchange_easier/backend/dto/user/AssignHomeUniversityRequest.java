package com.go_exchange_easier.backend.dto.user;

import jakarta.validation.constraints.NotNull;

public record AssignHomeUniversityRequest(

        @NotNull(message = "University id can not be null.")
        Short universityId

) { }
