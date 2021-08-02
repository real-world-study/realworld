package com.study.realworld.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtTokenConfig {

    @Value("${jwt.token.header-type}")
    private String headerType;

    @Value("${jwt.token.issuer}")
    private String issuer;

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.access-time}")
    private long accessTime;

    public String getHeaderType() {
        return headerType;
    }

    public String getIssuer() {
        return issuer;
    }

    public String getSecret() {
        return secret;
    }

    public long getAccessTime() {
        return accessTime;
    }

}
