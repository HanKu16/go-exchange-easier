package com.go_exchange_easier.backend.core.domain.user;

import com.go_exchange_easier.backend.core.domain.exchange.Exchange;
import com.go_exchange_easier.backend.core.domain.follow.university.UniversityFollow;
import com.go_exchange_easier.backend.core.domain.follow.user.UserFollow;
import com.go_exchange_easier.backend.core.domain.location.country.Country;
import com.go_exchange_easier.backend.core.domain.university.University;
import com.go_exchange_easier.backend.core.domain.university.review.entity.UniversityReview;
import com.go_exchange_easier.backend.core.domain.user.status.UserStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users", schema = "core")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SQLRestriction("deleted_at IS NULL")
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;

    @Column(name = "nick")
    private String nick;

    @Column(name = "avatar_key")
    private String avatarKey;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_status_id")
    private UserStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_of_origin_id")
    private Country countryOfOrigin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_university_id")
    private University homeUniversity;

    @OneToMany(mappedBy = "user")
    private Set<Exchange> exchanges = new HashSet<>();

    @OneToMany(mappedBy = "author")
    private Set<UniversityReview> universityReviews = new HashSet<>();

    @OneToMany(mappedBy = "follower")
    private Set<UserFollow> userFollowsSent = new HashSet<>();

    @OneToMany(mappedBy = "followee")
    private Set<UserFollow> userFollowsReceived = new HashSet<>();

    @OneToMany(mappedBy = "follower")
    private Set<UniversityFollow> universityFollowsSent = new HashSet<>();

}
