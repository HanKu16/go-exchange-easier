package com.go_exchange_easier.backend.annoations.docs.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Log out user from particular device")
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "204",
                description = "User successfully logout from device")
})
public @interface LogoutApiDocs { }
