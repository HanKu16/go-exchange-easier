package com.go_exchange_easier.backend.domain.user.avatar;

import com.go_exchange_easier.backend.domain.user.dto.AvatarKeys;
import com.go_exchange_easier.backend.domain.user.dto.AvatarUrlSummary;
import org.springframework.web.multipart.MultipartFile;

public interface AvatarService {

    AvatarKeys add(int userId, MultipartFile file);
    boolean delete(String key);
    AvatarUrlSummary getUrl(String key);

}
