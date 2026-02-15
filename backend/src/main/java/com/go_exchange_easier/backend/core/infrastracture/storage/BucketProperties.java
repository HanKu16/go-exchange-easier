package com.go_exchange_easier.backend.core.infrastracture.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Component
@ConfigurationProperties(prefix = "buckets")
@Data
public class BucketProperties {

    private String user;
    private String flag;

}
