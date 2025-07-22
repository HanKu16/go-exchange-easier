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
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nick")
    private String nick;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "deleted_at")
    private OffsetDateTime deletedAt;

    @OneToOne(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private UserDescription description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_status_id")
    private UserStatus status;

    @OneToOne(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private UserNotification notification;

    @OneToOne(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn
    private UserCredentials credentials;

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

    @OneToMany(mappedBy = "follower", cascade = CascadeType.REMOVE)
    private Set<UserFollow> userFollowsSent = new HashSet<>();

    @OneToMany(mappedBy = "followee", cascade = CascadeType.REMOVE)
    private Set<UserFollow> userFollowsReceived = new HashSet<>();

    @OneToMany(mappedBy = "follower", cascade = CascadeType.REMOVE)
    private Set<UniversityFollow> universityFollowsSent = new HashSet<>();

}
