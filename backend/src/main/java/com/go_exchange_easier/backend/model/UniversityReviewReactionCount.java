package com.go_exchange_easier.backend.model;

import com.go_exchange_easier.backend.model.keys.UniversityReviewsReactionCountId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "university_reviews_reaction_counts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IdClass(UniversityReviewsReactionCountId.class)
public class UniversityReviewReactionCount {

    @Id
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "university_review_id")
    private UniversityReview review;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reaction_type_id")
    private ReactionType reactionType;

    @Column(name = "count")
    private short count;

}
