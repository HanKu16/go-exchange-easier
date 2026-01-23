package com.go_exchange_easier.backend.service;

import org.springframework.web.multipart.MultipartFile;

public interface AvatarService {

    String add(int userId, MultipartFile file);
    boolean delete(String key);
    String getUrl(String key);

}
