package com.go_exchange_easier.backend.service;

import com.go_exchange_easier.backend.dto.university.CreateUniversityReviewRequest;
import com.go_exchange_easier.backend.dto.university.CreateUniversityReviewResponse;
import com.go_exchange_easier.backend.dto.universityReview.GetUniversityReviewResponse;
import java.util.List;

public interface UniversityReviewService {

    List<GetUniversityReviewResponse> getByAuthorId(
            int authorId, int currentUserId);
    CreateUniversityReviewResponse create(int userId,
            CreateUniversityReviewRequest request);
    void delete(int reviewId);

}
