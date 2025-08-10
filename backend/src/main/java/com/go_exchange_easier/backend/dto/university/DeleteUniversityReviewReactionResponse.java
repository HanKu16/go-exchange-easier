package com.go_exchange_easier.backend.dto.university;

import java.util.List;

public record DeleteUniversityReviewReactionResponse(

        Integer universityReviewId,
        List<UniversityReviewReactionDetail> reactionDetails

) { }
