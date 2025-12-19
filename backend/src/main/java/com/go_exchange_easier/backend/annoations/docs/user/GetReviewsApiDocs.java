package com.go_exchange_easier.backend.annoations.docs.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Get reviews which were written by user")
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Reviews were successfully returned"),
})
public @interface GetReviewsApiDocs { }
