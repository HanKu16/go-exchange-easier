package com.go_exchange_easier.backend.core.domain.follow.university;

public interface UniversityFollowService {

    void follow(Integer userId, Short universityId);
    void unfollow(Integer userId, Short universityId);
    boolean doesFollowExist(short universityId, int userId);

}
