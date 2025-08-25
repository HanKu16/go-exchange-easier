package com.go_exchange_easier.backend.service;

public interface UniversityFollowService {

    void follow(Integer userId, Short universityId);
    void unfollow(Integer userId, Short universityId);

}
