package com.go_exchange_easier.backend.service.impl;

import com.go_exchange_easier.backend.exception.base.FileUploadException;
import com.go_exchange_easier.backend.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class S3FileStorageService implements FileStorageService {

    private final S3Client s3Client;

    @Override
    public String uploadFile(String bucketName, String key,
            InputStream stream, long size, String contentType) {
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(contentType)
                .contentLength(size)
                .build();
        try {
            s3Client.putObject(request, RequestBody.fromInputStream(stream, size));
            return key;
        } catch (software.amazon.awssdk.core.exception.SdkException e) {
            throw new FileUploadException("AWS S3 error: " + e.getMessage());
        }
    }

    @Override
    public boolean deleteFile(String bucketName, String key) {
        try {
            s3Client.deleteObject(DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build());
        } catch (RuntimeException ex) {
            return false;
        }
        return true;
    }

}