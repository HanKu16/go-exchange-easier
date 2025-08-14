package com.go_exchange_easier.backend.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "university_reviews")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UniversityReview {

    @Id
    @Column(name = "university_review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "text_content")
    private String textContent;

    @Column(name = "star_rating")
    private short starRating;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "university_id")
    private University university;

    @OneToMany(mappedBy = "review", fetch = FetchType.EAGER)
    private Set<UniversityReviewReactionCount> reactionCounts = new HashSet<>();

    @OneToMany(mappedBy = "review")
    private Set<UniversityReviewReaction> reactions = new HashSet<>();

}
