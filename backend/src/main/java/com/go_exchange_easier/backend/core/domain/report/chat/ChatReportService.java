package com.go_exchange_easier.backend.core.domain.report.chat;

import com.go_exchange_easier.backend.core.domain.report.chat.dto.ChatReportSummary;
import com.go_exchange_easier.backend.core.domain.report.chat.dto.CreateChatReportRequest;

public interface ChatReportService {

    ChatReportSummary create(
            int reporterId,
            CreateChatReportRequest request
    );

}
