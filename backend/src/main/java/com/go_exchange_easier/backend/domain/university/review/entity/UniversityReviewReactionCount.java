package com.go_exchange_easier.backend.domain.university.review.entity;

import com.go_exchange_easier.backend.domain.reaction.ReactionType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;
import org.springframework.data.domain.Persistable;

@Entity
@Table(name = "university_reviews_reaction_counts")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@IdClass(UniversityReviewsReactionCountId.class)
public class UniversityReviewReactionCount implements
        Persistable<UniversityReviewsReactionCountId> {

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

    @Transient
    private boolean isNew = true;

    @Override
    public UniversityReviewsReactionCountId getId() {
        return new UniversityReviewsReactionCountId(review.getId(), type);
    }

    @PostLoad
    @PostPersist
    void markNotNew() {
        this.isNew = false;
    }

}
