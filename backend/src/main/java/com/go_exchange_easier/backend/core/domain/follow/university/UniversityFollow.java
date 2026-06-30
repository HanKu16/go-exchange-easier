package com.go_exchange_easier.backend.core.domain.follow.university;

import com.go_exchange_easier.backend.core.domain.university.University;
import com.go_exchange_easier.backend.core.domain.user.User;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "university_follows", schema = "core")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@IdClass(UniversityFollowId.class)
public class UniversityFollow {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id")
    private User follower;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_id")
    private University university;

}
