package com.go_exchange_easier.backend.core.domain.user.impl;

import com.go_exchange_easier.backend.common.exception.ReferencedResourceNotFoundException;
import com.go_exchange_easier.backend.common.exception.ResourceNotFoundException;
import com.go_exchange_easier.backend.core.domain.location.country.Country;
import com.go_exchange_easier.backend.core.domain.location.country.CountryRepository;
import com.go_exchange_easier.backend.core.domain.location.country.dto.CountrySummary;
import com.go_exchange_easier.backend.core.domain.university.University;
import com.go_exchange_easier.backend.core.domain.university.UniversityRepository;
import com.go_exchange_easier.backend.core.domain.university.dto.UniversitySummary;
import com.go_exchange_easier.backend.core.domain.user.User;
import com.go_exchange_easier.backend.core.domain.user.UserRepository;
import com.go_exchange_easier.backend.core.domain.user.UserUpdateService;
import com.go_exchange_easier.backend.core.domain.user.avatar.AvatarKeys;
import com.go_exchange_easier.backend.core.domain.user.avatar.AvatarService;
import com.go_exchange_easier.backend.core.domain.user.avatar.AvatarUrlSummary;
import com.go_exchange_easier.backend.core.domain.user.description.UpdateUserDescriptionRequest;
import com.go_exchange_easier.backend.core.domain.user.description.UserDescriptionDetails;
import com.go_exchange_easier.backend.core.domain.user.description.UserDescriptionRepository;
import com.go_exchange_easier.backend.core.domain.user.dto.AssignCountryOfOriginRequest;
import com.go_exchange_easier.backend.core.domain.user.dto.AssignHomeUniversityRequest;
import com.go_exchange_easier.backend.core.domain.user.status.UpdateUserStatusRequest;
import com.go_exchange_easier.backend.core.domain.user.status.UserStatus;
import com.go_exchange_easier.backend.core.domain.user.status.UserStatusRepository;
import com.go_exchange_easier.backend.core.domain.user.status.UserStatusSummary;
import java.time.OffsetDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserUpdateServiceImpl implements UserUpdateService {

    private final UserDescriptionRepository userDescriptionRepository;
    private final UniversityRepository universityRepository;
    private final CountryRepository countryRepository;
    private final UserStatusRepository userStatusRepository;
    private final UserRepository userRepository;
    private final AvatarService avatarService;

    @Override
    @Transactional
    @CacheEvict(value = "user-public-profiles", key = "'user:' + #userId")
    public UserDescriptionDetails updateDescription(
            int userId,
            UpdateUserDescriptionRequest request
    ) {
        OffsetDateTime updatedAt = OffsetDateTime.now();
        int rowsUpdated = userDescriptionRepository.updateByUserId(userId, request.description(), updatedAt);
        if (rowsUpdated == 0) {
            throw new ResourceNotFoundException("Description for user of id " + userId + " was not found.");
        }
        return new UserDescriptionDetails(userId, request.description(), updatedAt);
    }

    @Override
    @Transactional
    @CacheEvict(value = "user-public-profiles", key = "'user:' + #userId")
    public Optional<UniversitySummary> assignHomeUniversity(
            int userId,
            AssignHomeUniversityRequest request
    ) {
        if (request.universityId() != null) {
            University university = universityRepository.findById(request.universityId())
                    .orElseThrow(() -> new ReferencedResourceNotFoundException(
                            "University of id " + request.universityId() + " was not found."));
            int rowsUpdated = userRepository.updateHomeUniversity(userId, university.getId());
            if (rowsUpdated == 0) {
                throw new ResourceNotFoundException("User of id " + userId + " was not found.");
            }
            return Optional.of(new UniversitySummary(
                    university.getId(),
                    university.getOriginalName(),
                    university.getEnglishName()
            ));
        }
        int rowsUpdated = userRepository.updateHomeUniversity(userId, null);
        if (rowsUpdated == 0) {
            throw new ResourceNotFoundException("User of id " + userId + " was not found.");
        }
        return Optional.empty();

    }

    @Override
    @Transactional
    @CacheEvict(value = "user-public-profiles", key = "'user:' + #userId")
    public Optional<UserStatusSummary > updateStatus(
            int userId,
            UpdateUserStatusRequest request
    ) {
        if (request.statusId() != null) {
            UserStatus status = userStatusRepository.findById(request.statusId())
                    .orElseThrow(() -> new ReferencedResourceNotFoundException(
                            "Status of id " + request.statusId() + " was not found."));
            int rowsUpdated = userRepository.updateStatus(userId, status.getId());
            if (rowsUpdated == 0) {
                throw new ResourceNotFoundException("User of id " + userId + " was not found.");
            }
            return Optional.of(new UserStatusSummary(status.getId(), status.getName()));
        }
        int rowsUpdated = userRepository.updateStatus(userId, null);
        if (rowsUpdated == 0) {
            throw new ResourceNotFoundException("User of id " + userId + " was not found.");
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    @CacheEvict(value = "user-public-profiles", key = "'user:' + #userId")
    public Optional<CountrySummary> assignCountryOfOrigin(
            int userId,
            AssignCountryOfOriginRequest request
    ) {
        if (request.countryId() != null) {
            Country country = countryRepository.findById(request.countryId())
                    .orElseThrow(() -> new ReferencedResourceNotFoundException(
                            "Country of id " + request.countryId() + " was not found."));
            int rowsUpdated = userRepository.assignCountryOfOrigin(userId, request.countryId());
            if (rowsUpdated == 0) {
                throw new ResourceNotFoundException("User of id " + userId + " was not found.");
            }
            return Optional.of(new CountrySummary(country.getId(), country.getEnglishName()));
        } else {
            int rowsUpdated = userRepository.assignCountryOfOrigin(userId, null);
            if (rowsUpdated == 0) {
                throw new ResourceNotFoundException("User of id " + userId + " was not found.");
            }
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    @CacheEvict(value = "user-public-profiles", key = "'user:' + #userId")
    public AvatarUrlSummary addAvatar(
            int userId,
            MultipartFile file
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User of id " + userId + " was not found."));
        String existingAvatarKey = user.getAvatarKey();
        if (existingAvatarKey != null) {
            avatarService.delete(existingAvatarKey);
        }
        AvatarKeys newKeys = avatarService.add(userId, file);
        user.setAvatarKey(newKeys.original());
        return avatarService.getUrl(newKeys.original());
    }

    @Override
    @Transactional
    @CacheEvict(value = "user-public-profiles", key = "'user:' + #userId")
    public AvatarUrlSummary deleteAvatar(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User of id " + userId + " was not found."));
        String existingAvatarKey = user.getAvatarKey();
        if (existingAvatarKey != null) {
            avatarService.delete(existingAvatarKey);
            user.setAvatarKey(null);
        }
        return new AvatarUrlSummary(null, null);
    }

}
