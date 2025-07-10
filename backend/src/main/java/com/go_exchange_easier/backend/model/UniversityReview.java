package com.go_exchange_easier.backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "university_reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UniversityReview {

    @Id
    @Column(name = "university_review_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "text_content")
    private String textContent;

    @Column(name = "star_rating")
    private short starRating;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "university_id")
    private University university;

    @OneToMany(mappedBy = "review", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private Set<UniversityReviewsReactionCount> reactionCounts = new HashSet<>();

}
