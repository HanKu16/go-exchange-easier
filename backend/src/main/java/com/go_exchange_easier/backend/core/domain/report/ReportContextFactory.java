package com.go_exchange_easier.backend.core.domain.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.go_exchange_easier.backend.core.domain.university.review.dto.UniversityReviewSnapshot;
import com.go_exchange_easier.backend.core.domain.user.dto.UserPublicProfile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.Map;

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

}