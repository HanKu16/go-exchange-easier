package com.go_exchange_easier.backend.domain.university;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "university_reviews_reaction_counts")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@IdClass(UniversityReviewsReactionCountId.class)
public class UniversityReviewReactionCount {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_review_id")
    @EqualsAndHashCode.Include
    private UniversityReview review;

    @Id
    @ManyToOne
    @JoinColumn(name = "reaction_type_id")
    @EqualsAndHashCode.Include
    private ReactionType reactionType;

    @Column(name = "count")
    private short count;

}
