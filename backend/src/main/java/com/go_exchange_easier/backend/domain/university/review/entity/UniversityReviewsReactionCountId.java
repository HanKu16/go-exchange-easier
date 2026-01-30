package com.go_exchange_easier.backend.domain.university.review.entity;

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
    private Short reactionType;

}
