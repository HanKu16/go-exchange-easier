package com.go_exchange_easier.backend.core.domain.report.user;

import com.go_exchange_easier.backend.common.dto.error.ApiErrorResponse;
import com.go_exchange_easier.backend.core.domain.auth.dto.AuthenticatedUser;
import com.go_exchange_easier.backend.core.domain.report.user.dto.CreateUserReportRequest;
import com.go_exchange_easier.backend.core.domain.report.user.dto.UserReportSummary;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/reports/users")
@Tag(name = "User report")
public interface UserReportApi {

    @PostMapping("/{reportedUserId}")
    @Operation(summary = "Create user report")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "User report was successfully created"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation failed - invalid request body",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))),
            @ApiResponse(
                    responseCode = "404",
                    description = "Reported user was not found",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class)))
    })
    ResponseEntity<UserReportSummary> create(
            @PathVariable("reportedUserId") Integer reportedUserId,
            @RequestBody @Valid CreateUserReportRequest request,
            @AuthenticationPrincipal AuthenticatedUser authenticatedUser
    );

}