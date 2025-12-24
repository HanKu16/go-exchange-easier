package com.go_exchange_easier.backend.annoations.docs.university;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Get page of universities")
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Universities were successfully returned"
        )
})
public @interface GetPageApiDocs { }