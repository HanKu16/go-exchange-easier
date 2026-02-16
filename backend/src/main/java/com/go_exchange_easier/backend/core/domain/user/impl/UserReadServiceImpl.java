package com.go_exchange_easier.backend.core.domain.user.impl;

import com.go_exchange_easier.backend.common.exception.ResourceNotFoundException;
import com.go_exchange_easier.backend.core.api.CoreAvatar;
import com.go_exchange_easier.backend.core.api.CoreUser;
import com.go_exchange_easier.backend.core.domain.follow.user.UserFollow;
import com.go_exchange_easier.backend.core.domain.follow.user.UserFollowService;
import com.go_exchange_easier.backend.core.domain.university.dto.UniversityDetails;
import com.go_exchange_easier.backend.core.domain.university.impl.UniversityMapper;
import com.go_exchange_easier.backend.core.domain.user.*;
import com.go_exchange_easier.backend.core.domain.user.avatar.AvatarService;
import com.go_exchange_easier.backend.core.domain.user.avatar.AvatarUrlSummary;
import com.go_exchange_easier.backend.core.domain.user.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserReadServiceImpl implements UserReadService {

    private final UserPublicProfileProvider publicProfileProvider;
    private final UserFollowService userFollowService;
    private final UniversityMapper universityMapper;
    private final UserRepository userRepository;
    private final AvatarService avatarService;
    private final UserMapper userMapper;

    @Override
    public UserProfile getProfile(int userId, int currentUserId) {
        UserPublicProfile publicProfile = publicProfileProvider
                .getProfile(userId);
        boolean isFollowed = userFollowService
                .doesFollowExist(currentUserId, userId);
        return new UserProfile(
                publicProfile.userId(),
                publicProfile.nick(),
                publicProfile.avatarUrl(),
                publicProfile.description(),
                publicProfile.homeUniversity(),
                publicProfile.countryOfOrigin(),
                publicProfile.status(),
                isFollowed);
    }

    @Override
    public Page<UserDetails> getPage(String nick, Pageable pageable) {
        Page<User> users;
        Specification<User> spec = UserSpecification.fetchCountryOfOrigin()
                .and(UserSpecification.fetchHomeUniversityWithLocation());
        if (nick != null) {
            spec = spec.and(UserSpecification.hasNick(nick));
        }
        users = userRepository.findAll(spec, pageable);
        return users.map(userMapper::toDetails);
    }

    @Override
    public List<UserWithAvatarSummary> getFollowees(int userId) {
        User user = userRepository.findWithFollowees(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User of id " + userId + " was not found."));
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
    public List<UniversityDetails> getFollowedUniversities(int userId) {
        User user = userRepository.findWithFollowedUniversities(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User of id " + userId + " was not found."));
        return user.getUniversityFollowsSent()
                .stream()
                .map(f -> universityMapper.toDetails(f.getUniversity()))
                .toList();
    }

    @Override
    public UserWithAvatarSummary getMe(int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "User of id " + userId + " was not found."));
        String avatarUrl = user.getAvatarKey() != null ?
                avatarService.getUrl(user.getAvatarKey()).original() :
                null;
        return new UserWithAvatarSummary(user.getId(),
                user.getNick(), avatarUrl);
    }

    @Override
    public CoreUser getUser(int userId) {
        String thumbnailUrl = null;
        String originalUrl = null;
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            return CoreUser.UNKNOWN;
        }
        User user = optionalUser.get();
        if (user.getAvatarKey() != null) {
            AvatarUrlSummary avatar = avatarService
                    .getUrl(user.getAvatarKey());
            thumbnailUrl = avatar.thumbnail();
            originalUrl = avatar.original();
        }
        return new CoreUser(user.getId(), user.getNick(),
                new CoreAvatar(thumbnailUrl, originalUrl));
    }

    @Override
    public Map<Integer, CoreUser> getUsers(Set<Integer> userIds) {
        if (userIds.isEmpty()) {
            return Collections.emptyMap();
        }
        List<User> entities = userRepository.findAllById(userIds);
        Map<Integer, User> entitiesMap = entities.stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));
        Map<Integer, CoreUser> result = new HashMap<>();
        for (Integer id : userIds) {
            User user = entitiesMap.get(id);
            if (user == null) {
                result.put(id, CoreUser.UNKNOWN);
            } else {
                CoreAvatar userAvatar = null;
                if (user.getAvatarKey() != null) {
                    AvatarUrlSummary avatar = avatarService
                            .getUrl(user.getAvatarKey());
                    userAvatar = new CoreAvatar(avatar.original(),
                            avatar.thumbnail());
                }
                result.put(id, new CoreUser(
                        user.getId(),
                        user.getNick(),
                        userAvatar
                ));
            }
        }
        return result;
    }

}
