package com.go_exchange_easier.backend.service;

import com.go_exchange_easier.backend.dto.university.CreateUniversityReviewRequest;
import com.go_exchange_easier.backend.dto.university.CreateUniversityReviewResponse;

public interface UniversityReviewService {

    CreateUniversityReviewResponse create(int userId,
            CreateUniversityReviewRequest request);
    void delete(int reviewId);

}
