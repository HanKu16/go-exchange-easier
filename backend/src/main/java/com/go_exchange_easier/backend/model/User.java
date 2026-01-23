package com.go_exchange_easier.backend.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_credential_id")
    private UserCredentials credentials;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_description_id")
    private UserDescription description;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_notification_id")
    private UserNotification notification;

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

    @OneToMany(mappedBy = "user")
    private Set<RefreshToken> refreshTokens = new HashSet<>();

}
