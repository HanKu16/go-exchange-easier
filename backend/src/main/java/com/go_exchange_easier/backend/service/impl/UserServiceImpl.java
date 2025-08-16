package com.go_exchange_easier.backend.service.impl;

import com.go_exchange_easier.backend.dto.user.*;
import com.go_exchange_easier.backend.exception.base.ReferencedResourceNotFoundException;
import com.go_exchange_easier.backend.exception.domain.UserNotFoundException;
import com.go_exchange_easier.backend.model.University;
import com.go_exchange_easier.backend.model.UserStatus;
import com.go_exchange_easier.backend.repository.UniversityRepository;
import com.go_exchange_easier.backend.repository.UserDescriptionRepository;
import com.go_exchange_easier.backend.repository.UserRepository;
import com.go_exchange_easier.backend.repository.UserStatusRepository;
import com.go_exchange_easier.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDescriptionRepository userDescriptionRepository;
    private final UniversityRepository universityRepository;
    private final UserStatusRepository userStatusRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UpdateUserDescriptionResponse updateDescription(
            int userId, UpdateUserDescriptionRequest request) {
        OffsetDateTime updatedAt = OffsetDateTime.now();
        int rowsUpdated = userDescriptionRepository.updateByUserId(
                userId, request.description(), updatedAt);
        if (rowsUpdated == 0) {
            throw new UserNotFoundException("Description for user of id " +
                    userId + " was not found.");
        }
        return new UpdateUserDescriptionResponse(userId,
                request.description(), updatedAt);
    }

    @Override
    @Transactional
    public AssignHomeUniversityResponse assignHomeUniversity(
            int userId, AssignHomeUniversityRequest request) {
        University university = universityRepository.findById(request.universityId())
                .orElseThrow(() -> new ReferencedResourceNotFoundException(
                        "University of id " + request.universityId() +
                                " does not exist."));
        int rowsUpdated = userRepository.updateHomeUniversity(
                userId, university.getId());
        if (rowsUpdated == 0) {
            throw new UserNotFoundException("User of id "
                    + userId + " does not exist.");
        }
        return new AssignHomeUniversityResponse(userId, university.getId(),
                university.getOriginalName(), university.getEnglishName());
    }

    @Override
    @Transactional
    public UpdateUserStatusResponse updateStatus(
            int userId, UpdateUserStatusRequest request) {
        UserStatus status = userStatusRepository.findById(request.statusId())
                .orElseThrow(() -> new ReferencedResourceNotFoundException(
                        "Status of id " + request.statusId() +
                                " was not found."));
        int rowsUpdated = userRepository.updateStatus(userId, status.getId());
        if (rowsUpdated == 0) {
            throw new UserNotFoundException("User of id " + userId +
                    " was not found.");
        }
        return new UpdateUserStatusResponse(userId,
                status.getId(), status.getName());
    }

}
