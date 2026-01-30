package com.go_exchange_easier.backend.domain.university.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.OffsetDateTime;
import java.util.List;

@Schema(example = "Response body for creating university review")
public record CreateUniversityReviewResponse(

        Integer id,
        AuthorDto author,
        UniversityDto university,
        Short starRating,
        String textContent,
        OffsetDateTime createdAt,
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
