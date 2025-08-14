package com.go_exchange_easier.backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "university_review_reactions")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UniversityReviewReaction {

    @Id
    @Column(name = "university_review_reaction_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reaction_type_id")
    private ReactionType reactionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_review_id")
    private UniversityReview review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

}
