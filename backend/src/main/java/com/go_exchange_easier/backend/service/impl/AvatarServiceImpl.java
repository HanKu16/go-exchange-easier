package com.go_exchange_easier.backend.service.impl;

import com.go_exchange_easier.backend.config.BucketProperties;
import com.go_exchange_easier.backend.exception.base.FileUploadException;
import com.go_exchange_easier.backend.service.AvatarService;
import com.go_exchange_easier.backend.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.UUID;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AvatarServiceImpl implements AvatarService {

    private final FileStorageService fileStorageService;
    private final BucketProperties bucketProperties;

    @Override
    public String add(int userId, MultipartFile file)  {
        String fileName = file.getOriginalFilename();
        String extension = StringUtils.getFilenameExtension(fileName);
        String key = userId + "/" + UUID.randomUUID() + "." + extension;
        try {
            fileStorageService.upload(bucketProperties.getUser(),
                    key, file.getInputStream(), file.getSize(),
                    file.getContentType());
        } catch (IOException e) {
            throw new FileUploadException("Failed to upload avatar: " +
                    e.getMessage());
        }
        return key;
    }

    @Override
    public boolean delete(String key) {
        return fileStorageService.delete(bucketProperties.getUser(), key);
    }

    @Override
    public String getUrl(String key) {
        return fileStorageService.getUrl(bucketProperties.getUser(), key);
    }

}
