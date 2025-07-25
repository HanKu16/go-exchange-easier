package com.go_exchange_easier.backend.security;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.crypto.SecretKey;

@Configuration
public class JwtConfig {

    private final String jwtSecretKey;

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
