package com.go_exchange_easier.backend.core.domain.report.chat.impl;

import com.go_exchange_easier.backend.core.domain.auth.dto.AuthenticatedUser;
import com.go_exchange_easier.backend.core.domain.report.chat.ChatReportApi;
import com.go_exchange_easier.backend.core.domain.report.chat.ChatReportService;
import com.go_exchange_easier.backend.core.domain.report.chat.dto.CreateChatReportRequest;
import com.go_exchange_easier.backend.core.domain.report.chat.dto.ChatReportSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatReportController implements ChatReportApi {

    private final ChatReportService chatReportService;

    @Override
    public ResponseEntity<ChatReportSummary> create(
            CreateChatReportRequest request,
            AuthenticatedUser authenticatedUser
    ) {
        ChatReportSummary report = chatReportService.create(authenticatedUser.getId(), request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(report);
    }

}