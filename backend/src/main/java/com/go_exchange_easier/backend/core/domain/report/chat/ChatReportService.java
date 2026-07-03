package com.go_exchange_easier.backend.core.domain.report.chat;

import com.go_exchange_easier.backend.core.domain.report.chat.dto.ChatReportSummary;
import com.go_exchange_easier.backend.core.domain.report.chat.dto.CreateChatReportRequest;
import java.util.UUID;

public interface ChatReportService {

    ChatReportSummary create(
            UUID reporterId,
            CreateChatReportRequest request
    );

}
