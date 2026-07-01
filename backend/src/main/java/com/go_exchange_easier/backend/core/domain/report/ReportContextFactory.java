package com.go_exchange_easier.backend.core.domain.report;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.go_exchange_easier.backend.chat.api.ChatMessage;
import com.go_exchange_easier.backend.core.domain.university.review.dto.UniversityReviewSnapshot;
import com.go_exchange_easier.backend.core.domain.user.dto.UserPublicProfile;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReportContextFactory {

    private final ObjectMapper objectMapper;

    public Map<String, Object> createFromProfile(UserPublicProfile profile) {
        return objectMapper.convertValue(profile, new TypeReference<>() {});
    }

    public Map<String, Object> createFromReviewSnapshot(UniversityReviewSnapshot snapshot) {
        return objectMapper.convertValue(snapshot, new TypeReference<>() {});
    }

    public Map<String, Object> createFromMessages(List<ChatMessage> messages) {
        return objectMapper.convertValue(Map.of("messages", messages), new TypeReference<>() {});
    }

}