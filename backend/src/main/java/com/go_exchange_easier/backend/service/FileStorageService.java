package com.go_exchange_easier.backend.service;

import java.io.InputStream;

public interface FileStorageService {

    String uploadFile(String bucketName, String key, InputStream stream,
                      long size, String contentType);
    boolean deleteFile(String bucketName, String key);

}
