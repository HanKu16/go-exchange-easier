package com.go_exchange_easier.backend.dto.user;

import jakarta.validation.constraints.NotNull;

public record UpdateUserStatusRequest(

        @NotNull(message = "Status id can not be null.")
        Short statusId

) { }
