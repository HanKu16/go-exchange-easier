package com.go_exchange_easier.backend.core.domain.report;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/reports")
@Tag(name = "Report")
public interface ReportApi {

    @GetMapping("/dictionary")
    @Operation(summary = "Get report dictionary",
            description = "Returns possible values for reasons, statuses and report types")
    @ApiResponse(
            responseCode = "200",
            description = "Dictionary successfully retrieved"
    )
    ResponseEntity<ReportDictionary> getDictionary();

}