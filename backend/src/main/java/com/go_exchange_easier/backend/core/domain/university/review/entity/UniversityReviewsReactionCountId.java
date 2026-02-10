package com.go_exchange_easier.backend.core.domain.university.review.entity;

import com.go_exchange_easier.backend.core.domain.reaction.ReactionType;
import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UniversityReviewsReactionCountId implements Serializable {

    private Integer review;
    private ReactionType type;

}
