package com.go_exchange_easier.backend.core.domain.follow.user.impl;

import com.go_exchange_easier.backend.common.exception.IllegalOperationException;
import com.go_exchange_easier.backend.common.exception.ResourceAlreadyExistsException;
import com.go_exchange_easier.backend.common.exception.ResourceNotFoundException;
import com.go_exchange_easier.backend.core.domain.follow.user.UserFollowRepository;
import com.go_exchange_easier.backend.core.domain.follow.user.UserFollowService;
import com.go_exchange_easier.backend.core.domain.user.UserRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserFollowServiceImpl implements UserFollowService {

    private final UserFollowRepository userFollowRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void follow(
            UUID followerId,
            UUID followeeId
    ) {
        if (followerId.equals(followeeId)) {
            throw new IllegalOperationException("User can not follow himself.");
        }
        if (!userRepository.existsById(followeeId)) {
            throw new ResourceNotFoundException("User of id " + followeeId + " was not found.");
        }
        if (doesFollowExist(followerId, followeeId)) {
            throw new ResourceAlreadyExistsException(
                    "User follow where follower id " + followerId + " and followee id " + followeeId +
                            " already exists.");
        }
        userFollowRepository.insertByNativeQuery(followerId, followeeId);
    }

    @Override
    @Transactional
    public void unfollow(
            UUID followerId,
            UUID followeeId
    ) {
        int rowsDeleted = userFollowRepository.deleteByFollowerIdAndFolloweeId(followerId, followeeId);
        if (rowsDeleted == 0) {
            throw new ResourceNotFoundException(
                    "User follow where follower id " + followerId + " and followee id " + followeeId +
                            " was not found.");
        }
    }

    @Override
    public boolean doesFollowExist(
            UUID followerId,
            UUID followeeId
    ) {
        return userFollowRepository.countByFollowerIdAndFolloweeId(followerId, followeeId) > 0;
    }

}
