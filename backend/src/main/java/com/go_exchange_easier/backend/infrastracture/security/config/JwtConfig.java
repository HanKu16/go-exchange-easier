package com.go_exchange_easier.backend.infrastracture.security.config;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.crypto.SecretKey;

@Configuration
@ConfigurationProperties(prefix = "application.security.jwt")
@Getter
@Setter
public class JwtConfig {

    private long accessTokenValidityInSeconds;
    private long refreshTokenValidityInSeconds;
    private String jwtSecretKey;

    @Autowired
    public JwtConfig(@Value("${JWT_SECRET_KEY}") String secretKey) {
        this.jwtSecretKey = secretKey;
    }

    @Bean
    public SecretKey signingKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
