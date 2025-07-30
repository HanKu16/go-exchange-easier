package com.go_exchange_easier.backend.dto.user;

public record UpdateUserStatusResponse(

        Integer userId,
        Short statusId,
        String statusName

) { }
