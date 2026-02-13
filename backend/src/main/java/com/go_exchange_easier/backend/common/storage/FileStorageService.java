package com.go_exchange_easier.backend.common.storage;

import java.io.InputStream;

public interface FileStorageService {

    String upload(String bucketName, String key, InputStream stream,
                  long size, String contentType);
    boolean delete(String bucketName, String key);
    String getUrl(String bucketName, String key);

}
