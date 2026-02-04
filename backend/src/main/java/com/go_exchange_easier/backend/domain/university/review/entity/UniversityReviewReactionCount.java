package com.go_exchange_easier.backend.domain.university.review.entity;

import com.go_exchange_easier.backend.domain.reaction.ReactionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

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
    @EqualsAndHashCode.Include
    @Column(name = "reaction_type", columnDefinition = "reaction_type")
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private ReactionType type;

    @Column(name = "count")
    private short count;

}
