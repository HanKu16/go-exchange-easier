package com.go_exchange_easier.backend.core.domain.report.chat.impl;

import com.go_exchange_easier.backend.chat.api.ChatFacade;
import com.go_exchange_easier.backend.chat.api.ChatMessage;
import com.go_exchange_easier.backend.common.dto.SimplePage;
import com.go_exchange_easier.backend.common.exception.NotOwnerOfResourceException;
import com.go_exchange_easier.backend.core.domain.report.ReportContextFactory;
import com.go_exchange_easier.backend.core.domain.report.ReportStatus;
import com.go_exchange_easier.backend.core.domain.report.ReportType;
import com.go_exchange_easier.backend.core.domain.report.chat.ChatReport;
import com.go_exchange_easier.backend.core.domain.report.chat.ChatReportRepository;
import com.go_exchange_easier.backend.core.domain.report.chat.ChatReportService;
import com.go_exchange_easier.backend.core.domain.report.chat.dto.ChatReportSummary;
import com.go_exchange_easier.backend.core.domain.report.chat.dto.CreateChatReportRequest;
import java.time.OffsetDateTime;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatReportServiceImpl implements ChatReportService {

    private final ChatReportRepository chatReportRepository;
    private final ReportContextFactory reportContextFactory;
    private final ChatFacade chatFacade;

    @Override
    @Transactional
    public ChatReportSummary create(
            int reporterId,
            CreateChatReportRequest request
    ) {
        boolean isUserMemberOfRoom = chatFacade.isUserMemberOfRoom(reporterId, request.roomId());
        if (!isUserMemberOfRoom) {
            throw new NotOwnerOfResourceException("User is not member of room that he is trying to report.");
        }
        boolean isReportedUserMemberOfRoom = chatFacade.isUserMemberOfRoom(request.reportedUserId(), request.roomId());
        if (!isReportedUserMemberOfRoom) {
            throw new IllegalArgumentException("User is trying to report user that is not member of room.");
        }
        ChatReport report = new ChatReport();
        report.setCreatedAt(OffsetDateTime.now());
        report.setDescription(request.description());
        report.setStatus(ReportStatus.NEW);
        report.setReason(request.reason());
        report.setType(ReportType.CHAT);
        report.setReporterId(reporterId);
        report.setReportedUserId(request.reportedUserId());
        report.setRoomId(request.roomId());
        Pageable pageable = PageRequest.of(
                0,
                20,
                Sort.by("createdAt")
                        .descending()
        );
        SimplePage<ChatMessage> page = chatFacade.getPageOfMessages(reporterId, request.roomId(), pageable);
        Map<String, Object> context = reportContextFactory.createFromMessages(page.content());
        report.setContext(context);
        ChatReport savedReport = chatReportRepository.save(report);
        return ChatReportSummary.fromEntity(savedReport);
    }

}
