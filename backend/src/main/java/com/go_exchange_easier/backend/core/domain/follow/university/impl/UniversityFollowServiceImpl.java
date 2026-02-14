package com.go_exchange_easier.backend.core.domain.follow.university.impl;

import com.go_exchange_easier.backend.common.exception.ResourceAlreadyExistsException;
import com.go_exchange_easier.backend.common.exception.ResourceNotFoundException;
import com.go_exchange_easier.backend.core.domain.follow.university.UniversityFollowRepository;
import com.go_exchange_easier.backend.core.domain.follow.university.UniversityFollowService;
import com.go_exchange_easier.backend.core.domain.university.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UniversityFollowServiceImpl implements UniversityFollowService {

    private final UniversityFollowRepository universityFollowRepository;
    private final UniversityRepository universityRepository;

    @Override
    @Transactional
    public void follow(Integer userId, Short universityId) {
        if (!universityRepository.existsById(universityId)) {
            throw new ResourceNotFoundException("University of id " +
                    universityId + " was not found.");
        }
        if (doesFollowExist(universityId, userId)) {
            throw new ResourceAlreadyExistsException("University follow where user id" +
                    userId + " and university id " + universityId + " already exists.");
        }
        universityFollowRepository.insertByNativeQuery(universityId, userId);
    }

    @Override
    @Transactional
    public void unfollow(Integer userId, Short universityId) {
        int rowsDeleted = universityFollowRepository.deleteByUniversityIdAndFollowerId(
                universityId, userId);
        if (rowsDeleted == 0) {
            throw new ResourceNotFoundException("University follow" +
                    " where user id " + userId + " and university id " +
                    universityId + " was not found.");
        }
    }

    private boolean doesFollowExist(short universityId, int userId) {
        return universityFollowRepository.countByUniversityIdAndFollowerIdNativeQuery(
                universityId, userId) > 0;
    }

}
