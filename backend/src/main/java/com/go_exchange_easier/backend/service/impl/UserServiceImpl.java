package com.go_exchange_easier.backend.service.impl;

import com.go_exchange_easier.backend.dto.user.*;
import com.go_exchange_easier.backend.exception.base.ReferencedResourceNotFoundException;
import com.go_exchange_easier.backend.exception.domain.UserDescriptionNotFoundException;
import com.go_exchange_easier.backend.exception.domain.UserNotFoundException;
import com.go_exchange_easier.backend.model.*;
import com.go_exchange_easier.backend.repository.UniversityRepository;
import com.go_exchange_easier.backend.repository.UserDescriptionRepository;
import com.go_exchange_easier.backend.repository.UserRepository;
import com.go_exchange_easier.backend.repository.UserStatusRepository;
import com.go_exchange_easier.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDescriptionRepository userDescriptionRepository;
    private final UniversityRepository universityRepository;
    private final UserStatusRepository userStatusRepository;
    private final UserRepository userRepository;

    @Override
    public GetUserProfileResponse getProfile(int userId, int currentUserId) {
        List<Object[]> rows = userRepository.findProfileById(userId, currentUserId);
        if (rows.isEmpty()) {
            throw new UserNotFoundException("User of id " +
                    userId + " was not found");
        }
        Object[] row = rows.getFirst();
        Integer id = (Integer) row[0];
        String nick = (String) row[1];
        String description = (String) row[2];
        Short universityId = (Short) row[3];
        String universityOriginalName = (String) row[4];
        String universityEnglishName = (String) row[5];
        Short countryId = (Short) row[6];
        String countryName = (String) row[7];
        Short statusId = (Short) row[8];
        String statusName = (String) row[9];
        Boolean isFollowed = (Boolean) row[10];
        GetUserProfileResponse.UniversityDto university =
                new GetUserProfileResponse.UniversityDto(universityId,
                        universityOriginalName, universityEnglishName);
        GetUserProfileResponse.CountryDto country =
                new GetUserProfileResponse.CountryDto(
                        countryId, countryName);
        GetUserProfileResponse.StatusDto status =
                new GetUserProfileResponse.StatusDto(statusId, statusName);
        return new GetUserProfileResponse(id, nick, description, isFollowed,
                university, country, status);
    }

    @Override
    @Transactional
    public UpdateUserDescriptionResponse updateDescription(
            int userId, UpdateUserDescriptionRequest request) {
        OffsetDateTime updatedAt = OffsetDateTime.now();
        int rowsUpdated = userDescriptionRepository.updateByUserId(
                userId, request.description(), updatedAt);
        if (rowsUpdated == 0) {
            throw new UserDescriptionNotFoundException("Description for user of id " +
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
