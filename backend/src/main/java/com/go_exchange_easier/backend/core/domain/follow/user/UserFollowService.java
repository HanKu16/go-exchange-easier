package com.go_exchange_easier.backend.core.domain.follow.user;

import java.util.UUID;

public interface UserFollowService {

    void follow(
            UUID followerId,
            UUID followeeId
    );

    void unfollow(
            UUID followerId,
            UUID followeeId
    );

    boolean doesFollowExist(
            UUID followerId,
            UUID followeeId
    );

}
