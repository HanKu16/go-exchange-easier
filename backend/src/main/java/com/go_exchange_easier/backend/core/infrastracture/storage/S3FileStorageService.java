package com.go_exchange_easier.backend.core.infrastracture.storage;

import com.go_exchange_easier.backend.common.exception.FileUploadException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import java.io.InputStream;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class S3FileStorageService implements FileStorageService {

    private final S3Presigner presigner;
    private final S3Client s3Client;

    @Override
    public String upload(String bucketName, String key,
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
    public boolean delete(String bucketName, String key) {
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

    @Override
    public String getUrl(String bucketName, String key) {
        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(60))
                .getObjectRequest(b -> b.bucket(bucketName).key(key))
                .build();
        return presigner.presignGetObject(presignRequest).url().toString();
    }

}