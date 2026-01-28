package com.go_exchange_easier.backend.domain.follow;

public interface UniversityFollowService {

    void follow(Integer userId, Short universityId);
    void unfollow(Integer userId, Short universityId);

}
