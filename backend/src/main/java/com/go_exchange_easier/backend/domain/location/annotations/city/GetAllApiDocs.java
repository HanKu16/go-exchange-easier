package com.go_exchange_easier.backend.domain.location.annotations.city;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Get all cities")
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Cities were successfully returned"),
})
public @interface GetAllApiDocs { }

