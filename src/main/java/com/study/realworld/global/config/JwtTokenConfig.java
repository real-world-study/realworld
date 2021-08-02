package com.study.realworld.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtTokenConfig {

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.access-time}")
    private long accessTime;

    @Value("${jwt.token.authorites-key}")
    private String authoritesKey;

    public String getSecret() {
        return secret;
    }

    public long getAccessTime() {
        return accessTime;
    }

    public String getAuthoritesKey() {
        return authoritesKey;
    }
}
