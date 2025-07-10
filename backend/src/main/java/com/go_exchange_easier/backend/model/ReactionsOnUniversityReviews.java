package com.go_exchange_easier.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reactions_on_university_reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReactionsOnUniversityReviews {

    @Id
    @Column(name = "reaction_on_university_review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "reaction_type_id")
    private ReactionType reactionTypeId;

    @ManyToOne
    @JoinColumn(name = "university_review_id")
    private UniversityReview review;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

}
