package com.go_exchange_easier.backend.domain.follow;

public interface UserFollowService {

    void follow(int followerId, int followeeId);
    void unfollow(int followerId, int followeeId);

}
