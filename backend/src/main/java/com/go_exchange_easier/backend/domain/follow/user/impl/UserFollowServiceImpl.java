package com.go_exchange_easier.backend.domain.follow.user.impl;

import com.go_exchange_easier.backend.common.exception.IllegalOperationException;
import com.go_exchange_easier.backend.common.exception.ResourceAlreadyExistsException;
import com.go_exchange_easier.backend.common.exception.ResourceNotFoundException;
import com.go_exchange_easier.backend.domain.follow.user.UserFollowRepository;
import com.go_exchange_easier.backend.domain.follow.user.UserFollowService;
import com.go_exchange_easier.backend.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserFollowServiceImpl implements UserFollowService {

    private final UserFollowRepository userFollowRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void follow(int followerId, int followeeId) {
        if (followerId == followeeId) {
            throw new IllegalOperationException("User can not follow himself.");
        }
        if (!userRepository.existsById(followeeId)) {
            throw new ResourceNotFoundException("User of id " +
                    followeeId + " was not found.");
        }
        if (doesFollowExist(followerId, followeeId)) {
            throw new ResourceAlreadyExistsException("User follow where follower id " +
                    followeeId + " and followee id " + followeeId + " already exists.");
        }
        userFollowRepository.insertByNativeQuery(followerId, followeeId);
    }

    @Override
    @Transactional
    public void unfollow(int followerId, int followeeId) {
        int rowsDeleted = userFollowRepository.deleteByFollowerIdAndFolloweeId(
                followerId, followeeId);
        if (rowsDeleted == 0) {
            throw new ResourceNotFoundException("User follow" +
                    " where follower id " + followerId + " and followee id " +
                    followeeId + " was not found.");
        }
    }

    private boolean doesFollowExist(int followerId, int followeeId) {
        return userFollowRepository.countByFollowerIdAndFolloweeId(
                followerId, followeeId) > 0;
    }

}
