package com.go_exchange_easier.backend.core.domain.follow.university;

import java.util.UUID;

public interface UniversityFollowService {

    void follow(
            UUID userId,
            Short universityId
    );

    void unfollow(
            UUID userId,
            Short universityId
    );

    boolean doesFollowExist(
            short universityId,
            UUID userId
    );

}
