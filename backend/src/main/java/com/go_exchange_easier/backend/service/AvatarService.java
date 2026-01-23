package com.go_exchange_easier.backend.service;

import com.go_exchange_easier.backend.dto.user.AvatarKeys;
import com.go_exchange_easier.backend.dto.user.AvatarUrlSummary;
import org.springframework.web.multipart.MultipartFile;

public interface AvatarService {

    AvatarKeys add(int userId, MultipartFile file);
    boolean delete(String key);
    AvatarUrlSummary getUrl(String key);

}
