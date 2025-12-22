package com.go_exchange_easier.backend.service;

import com.go_exchange_easier.backend.dto.universityReview.CreateUniversityReviewRequest;
import com.go_exchange_easier.backend.dto.universityReview.CreateUniversityReviewResponse;
import com.go_exchange_easier.backend.dto.university.GetReviewsCountResponse;
import com.go_exchange_easier.backend.dto.universityReview.GetUniversityReviewResponse;
import java.util.List;

public interface UniversityReviewService {

    List<GetUniversityReviewResponse> getByAuthorId(
            int authorId, int currentUserId);
    List<GetUniversityReviewResponse> getByUniversityId(
            int universityId, int currentUserId, int page, int size);
    CreateUniversityReviewResponse create(int userId,
            CreateUniversityReviewRequest request);
    void delete(int reviewId);
    GetReviewsCountResponse countByUniversityId(int universityId);

}
