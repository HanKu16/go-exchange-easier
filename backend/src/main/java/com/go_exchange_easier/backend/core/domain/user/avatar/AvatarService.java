package com.go_exchange_easier.backend.core.domain.user.avatar;

import org.springframework.web.multipart.MultipartFile;

public interface AvatarService {

    AvatarKeys add(int userId, MultipartFile file);
    boolean delete(String key);
    AvatarUrlSummary getUrl(String key);

}
