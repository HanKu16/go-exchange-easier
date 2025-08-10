package com.go_exchange_easier.backend.dto.university;

import java.util.List;

public record AddUniversityReviewReactionResponse(

        Integer universityReviewId,
        List<UniversityReviewReactionDetail> reactionDetails

) { }
