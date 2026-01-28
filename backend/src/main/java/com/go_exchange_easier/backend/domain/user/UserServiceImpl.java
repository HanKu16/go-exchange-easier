package com.go_exchange_easier.backend.domain.user;

import com.go_exchange_easier.backend.domain.follow.UserFollow;
import com.go_exchange_easier.backend.domain.location.Country;
import com.go_exchange_easier.backend.domain.location.CountryRepository;
import com.go_exchange_easier.backend.domain.university.University;
import com.go_exchange_easier.backend.domain.university.UniversityRepository;
import com.go_exchange_easier.backend.domain.university.dto.UniversityDetails;
import com.go_exchange_easier.backend.domain.user.avatar.AvatarService;
import com.go_exchange_easier.backend.domain.user.description.UserDescriptionRepository;
import com.go_exchange_easier.backend.domain.user.dto.*;
import com.go_exchange_easier.backend.domain.user.status.UserStatus;
import com.go_exchange_easier.backend.domain.user.status.UserStatusRepository;
import com.go_exchange_easier.backend.common.exception.ReferencedResourceNotFoundException;
import com.go_exchange_easier.backend.domain.user.description.UserDescriptionNotFoundException;
import com.go_exchange_easier.backend.domain.user.specification.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDescriptionRepository userDescriptionRepository;
    private final UniversityRepository universityRepository;
    private final CountryRepository countryRepository;
    private final UserStatusRepository userStatusRepository;
    private final UserRepository userRepository;
    private final AvatarService avatarService;
    private static final Logger logger = LogManager.getLogger(
            UserServiceImpl.class);

    @Override
    @Transactional(readOnly = true)
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
        String avatarKey = row[11] != null ? (String) row[11] : null;
        String avatarUrl = null;
        if (avatarKey != null) {
            avatarUrl = avatarService.getUrl(avatarKey).original();
        }
        GetUserProfileResponse.UniversityDto university =
                universityId != null ?
                new GetUserProfileResponse.UniversityDto(universityId,
                        universityOriginalName, universityEnglishName) :
                null;
        GetUserProfileResponse.CountryDto country =
                countryId != null ?
                new GetUserProfileResponse.CountryDto(
                        countryId, countryName) :
                null;
        GetUserProfileResponse.StatusDto status =
                statusId != null ?
                new GetUserProfileResponse.StatusDto(statusId, statusName) :
                null;
        return new GetUserProfileResponse(id, nick, avatarUrl, description, isFollowed,
                university, country, status);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDetails> getPage(String nick, Pageable pageable) {
        Page<User> users;
        Specification<User> spec = UserSpecification.fetchCountryOfOrigin()
                .and(UserSpecification.fetchHomeUniversity());
        if (nick != null) {
            spec = UserSpecification.hasNick(nick);
        }
        users = userRepository.findAll(spec, pageable);
        return users.map(UserDetails::fromEntity);
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
        if (request.statusId() != null) {
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
        int rowsUpdated = userRepository.updateStatus(userId, null);
        if (rowsUpdated == 0) {
            throw new UserNotFoundException("User of id " + userId +
                    " was not found.");
        }
        return new UpdateUserStatusResponse(userId, null, null);
    }

    @Override
    @Transactional
    public AssignCountryOfOriginResponse assignCountryOfOrigin(
            int userId, AssignCountryOfOriginRequest request) {
        if (request.countryId() != null) {
            Country country = countryRepository.findById(request.countryId())
                    .orElseThrow(() -> new ReferencedResourceNotFoundException(
                            "Country of id " + request.countryId() +
                                    " was not found."));
            int rowsUpdated = userRepository.assignCountryOfOrigin(
                    userId, request.countryId());
            if (rowsUpdated == 0) {
                throw new UserNotFoundException("User of id " + userId +
                        " was not found.");
            }
            return new AssignCountryOfOriginResponse(userId,
                    new AssignCountryOfOriginResponse.CountryDto(
                            country.getId(), country.getEnglishName()));
        } else {
            int rowsUpdated = userRepository.assignCountryOfOrigin(userId, null);
            if (rowsUpdated == 0) {
                throw new UserNotFoundException("User of id " + userId +
                        " was not found.");
            }
            return new AssignCountryOfOriginResponse(userId, null);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserWithAvatarSummary> getFollowees(int userId) {
        User user = userRepository.findWithFollowees(userId)
                .orElseThrow(() -> new UserNotFoundException(
                "User of id " + userId +
                        " was not found."));
        return user.getUserFollowsSent()
                .stream()
                .map(UserFollow::getFollowee)
                .map(u -> new UserWithAvatarSummary(
                        u.getId(), u.getNick(),
                        u.getAvatarKey() != null ?
                            avatarService.getUrl(u.getAvatarKey()).thumbnail() :
                            null))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<UniversityDetails> getFollowedUniversities(int userId) {
        User user = userRepository.findWithFollowedUniversities(userId)
                .orElseThrow(() -> new UserNotFoundException(
                        "User of id " + userId +
                                " was not found."));
        return user.getUniversityFollowsSent()
                .stream()
                .map(f -> UniversityDetails.fromEntity(f.getUniversity()))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UserWithAvatarSummary getMe(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User of id " + userId +
                        " was not found."));
        String avatarUrl = user.getAvatarKey() != null ?
                avatarService.getUrl(user.getAvatarKey()).original() :
                null;
        return new UserWithAvatarSummary(user.getId(), user.getNick(), avatarUrl);
    }

    @Override
    @Transactional
    public AvatarUrlSummary addAvatar(int userId, MultipartFile file) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User of id " + userId +
                        " was not found."));
        String oldOriginalKey = user.getAvatarKey();
        AvatarKeys newKeys = avatarService.add(userId, file);
        if (oldOriginalKey != null) {
            boolean wasOldAvatarDeleted = avatarService.delete(oldOriginalKey);
            if (!wasOldAvatarDeleted) {
                logger.warn("Failed to delete old avatar from S3 which key is {}", oldOriginalKey);
            }
        }
        user.setAvatarKey(newKeys.original());
        return avatarService.getUrl(newKeys.original());
    }

    @Override
    @Transactional
    public AvatarUrlSummary deleteAvatar(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User of id " + userId +
                        " was not found."));
        String key = user.getAvatarKey();
        user.setAvatarKey(null);
        if (key != null) {
            boolean wasOldAvatarDeleted = avatarService.delete(key);
            if (!wasOldAvatarDeleted) {
                logger.warn("Failed to delete avatar from S3 which key is {}", key);
            }
        }
        return new AvatarUrlSummary(null, null);
    }

}
