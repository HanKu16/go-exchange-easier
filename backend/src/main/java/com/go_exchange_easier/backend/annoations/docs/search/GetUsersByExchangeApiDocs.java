package com.go_exchange_easier.backend.annoations.docs.search;

import com.go_exchange_easier.backend.dto.error.ApiErrorResponse;
import com.go_exchange_easier.backend.dto.exchange.GetUserExchangeResponse;
import com.go_exchange_easier.backend.dto.search.GetUsersByExchangeResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "Get users that fulfill given exchange criteria")
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Users were successfully returned",
                content = @Content(mediaType = "application/json",
                        array = @ArraySchema(
                                schema = @Schema(implementation = GetUsersByExchangeResponse.class)))),
        @ApiResponse(
                responseCode = "400",
                description = "Bad number of filters were applied",
                content = @Content(mediaType = "application/json",
                        array = @ArraySchema(
                                schema = @Schema(implementation = ApiErrorResponse.class)))),
})
public @interface GetUsersByExchangeApiDocs {
}
