package com.go_exchange_easier.backend.service.impl;

import com.go_exchange_easier.backend.dto.user.*;
import com.go_exchange_easier.backend.exception.UniversityDoesNotExistException;
import com.go_exchange_easier.backend.exception.UserDoesNotExistException;
import com.go_exchange_easier.backend.exception.UserStatusDoesNotExistException;
import com.go_exchange_easier.backend.model.University;
import com.go_exchange_easier.backend.model.User;
import com.go_exchange_easier.backend.model.UserDescription;
import com.go_exchange_easier.backend.model.UserStatus;
import com.go_exchange_easier.backend.repository.UniversityRepository;
import com.go_exchange_easier.backend.repository.UserDescriptionRepository;
import com.go_exchange_easier.backend.repository.UserRepository;
import com.go_exchange_easier.backend.repository.UserStatusRepository;
import com.go_exchange_easier.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDescriptionRepository userDescriptionRepository;
    private final UniversityRepository universityRepository;
    private final UserStatusRepository userStatusRepository;

    @Override
    public UpdateUserDescriptionResponse updateDescription(
            int userId, UpdateDescriptionRequest request) {
        UserDescription description = userDescriptionRepository.findById(userId)
                .orElseThrow(() -> new UserDoesNotExistException(
                        "Description for user of id " + userId + " was not found. " +
                                "Probably user of given id does not exist."));
        description.setTextContent(request.description());
        description.setUpdatedAt(OffsetDateTime.now());
        UserDescription updatedDescription = userDescriptionRepository.save(description);
        return new UpdateUserDescriptionResponse(userId,
                updatedDescription.getTextContent(),
                updatedDescription.getUpdatedAt());
    }

    @Override
    public AssignHomeUniversityResponse assignHomeUniversity(
            int userId, AssignHomeUniversityRequest request) {
        University university = universityRepository.findById(request.universityId())
                .orElseThrow(() -> new UniversityDoesNotExistException("University " +
                                "of id " + request.universityId() + " does not exist."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserDoesNotExistException(
                        "User of id " + userId + " does not exist."));
        user.setHomeUniversity(university);
        userRepository.save(user);
        return new AssignHomeUniversityResponse(userId, university.getId(),
                university.getOriginalName(), university.getEnglishName());
    }

    @Override
    public UpdateUserStatusResponse updateStatus(
            int userId, UpdateUserStatusRequest request) {
        UserStatus status = userStatusRepository.findById(request.statusId())
                .orElseThrow(() -> new UserStatusDoesNotExistException("Status " +
                        "of id " + request.statusId() + " does not exist."));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserDoesNotExistException(
                        "User of id " + userId + " does not exist."));
        user.setStatus(status);
        userRepository.save(user);
        return new UpdateUserStatusResponse(userId,
                status.getId(), status.getName());
    }

}
