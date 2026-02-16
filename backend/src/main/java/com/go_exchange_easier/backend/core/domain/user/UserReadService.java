package com.go_exchange_easier.backend.core.domain.user;

import com.go_exchange_easier.backend.core.api.CoreUser;
import com.go_exchange_easier.backend.core.domain.university.dto.UniversityDetails;
import com.go_exchange_easier.backend.core.domain.user.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserReadService {

    UserProfile getProfile(int userId, int currentUserId);
    Page<UserDetails> getPage(String nick, Pageable pageable);
    List<UserWithAvatarSummary> getFollowees(int userId);
    List<UniversityDetails> getFollowedUniversities(int userId);
    UserWithAvatarSummary getMe(int userId);
    CoreUser getUser(int userId);
    Map<Integer, CoreUser> getUsers(Set<Integer> userIds);

}
