package com.go_exchange_easier.backend.service.impl;

import com.go_exchange_easier.backend.exception.base.ReferencedResourceNotFoundException;
import com.go_exchange_easier.backend.exception.base.ResourceAlreadyExistsException;
import com.go_exchange_easier.backend.exception.domain.UniversityFollowNotFoundException;
import com.go_exchange_easier.backend.repository.UniversityFollowRepository;
import com.go_exchange_easier.backend.repository.UniversityRepository;
import com.go_exchange_easier.backend.repository.UserRepository;
import com.go_exchange_easier.backend.service.UniversityFollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UniversityFollowServiceImpl implements UniversityFollowService {

    private final UniversityFollowRepository universityFollowRepository;
    private final UniversityRepository universityRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void follow(Integer userId, Short universityId) {
        if (!userRepository.existsById(userId)) {
            throw new ReferencedResourceNotFoundException(
                    "User of id " + userId + " was not found.");
        }
        if (!universityRepository.existsById(universityId)) {
            throw new ReferencedResourceNotFoundException(
                    "University of id " + universityId + " was not found.");
        }
        if (doesFollowExist(universityId, userId)) {
            throw new ResourceAlreadyExistsException("University follow where " +
                    userId + " and " + universityId + " already exists.");
        }
        universityFollowRepository.insertByNativeQuery(universityId, userId);
    }

    @Override
    @Transactional
    public void unfollow(Integer userId, Short universityId) {
        int rowsDeleted = universityFollowRepository.deleteById(
                universityId, userId);
        if (rowsDeleted == 0) {
            throw new UniversityFollowNotFoundException("University follow where " +
                    userId + " and " + universityId + " was not found.");
        }
    }

    private boolean doesFollowExist(short universityId, int userId) {
        return universityFollowRepository.countByUniversityIdAndFollowerIdNativeQuery(
                universityId, userId) > 0;
    }

}
