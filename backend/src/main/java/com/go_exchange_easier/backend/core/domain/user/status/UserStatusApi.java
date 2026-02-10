package com.go_exchange_easier.backend.core.domain.user.status;

import com.go_exchange_easier.backend.core.common.dto.Listing;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/userStatuses")
@Tag(name = "User status")
public interface UserStatusApi {

    @GetMapping
    @Operation(summary = "Get user statuses")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Statuses were successfully returned")
    })
    ResponseEntity<Listing<UserStatusSummary>> getAll();

}
