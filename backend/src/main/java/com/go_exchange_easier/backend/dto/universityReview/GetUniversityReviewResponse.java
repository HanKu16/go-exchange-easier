package com.go_exchange_easier.backend.dto.universityReview;

import java.time.Instant;
import java.util.List;

public record GetUniversityReviewResponse(

        Integer id,
        AuthorDto author,
        UniversityDto university,
        Short starRating,
        String textContent,
        Instant createdAt,
        List<ReactionDetailDto> reactions

) {

    public record AuthorDto(

            Integer id,
            String nick

    ) {}

    public record UniversityDto(

            Short id,
            String englishName,
            String nativeName

    ) {}

    public record ReactionDetailDto(

            Short typeId,
            String name,
            Short count,
            Boolean isSet

    ) {}

}
