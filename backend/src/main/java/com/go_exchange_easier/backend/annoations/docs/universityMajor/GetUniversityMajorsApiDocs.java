package com.go_exchange_easier.backend.annoations.docs.universityMajor;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Get all university majors")
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "University majors were successfully returned"),
})
public @interface GetUniversityMajorsApiDocs { }
