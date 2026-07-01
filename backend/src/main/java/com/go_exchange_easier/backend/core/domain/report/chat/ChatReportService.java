package com.go_exchange_easier.backend.core.domain.report.chat;

import com.go_exchange_easier.backend.core.domain.report.chat.dto.ChatReportDetails;
import com.go_exchange_easier.backend.core.domain.report.chat.dto.CreateChatReportRequest;

public interface ChatReportService {

    ChatReportDetails create(
            int reporterId,
            CreateChatReportRequest request
    );

}
