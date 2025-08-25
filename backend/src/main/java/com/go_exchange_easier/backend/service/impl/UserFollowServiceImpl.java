package com.go_exchange_easier.backend.service.impl;

import com.go_exchange_easier.backend.exception.base.ResourceAlreadyExistsException;
import com.go_exchange_easier.backend.exception.domain.UserFollowNotFoundException;
import com.go_exchange_easier.backend.exception.domain.UserNotFoundException;
import com.go_exchange_easier.backend.repository.UserFollowRepository;
import com.go_exchange_easier.backend.repository.UserRepository;
import com.go_exchange_easier.backend.service.UserFollowService;
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
        if (!userRepository.existsById(followeeId)) {
            throw new UserNotFoundException("User of id " +
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
            throw new UserFollowNotFoundException("User follow" +
                    " where follower id " + followerId + " and followee id " +
                    followeeId + " was not found.");
        }
    }

    private boolean doesFollowExist(int followerId, int followeeId) {
        return userFollowRepository.countByFollowerIdAndFolloweeId(
                followerId, followeeId) > 0;
    }

}
