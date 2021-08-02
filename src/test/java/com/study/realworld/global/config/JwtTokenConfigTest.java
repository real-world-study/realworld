package com.study.realworld.global.config;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * JwtTokenConfig value 설정값 확인을 위한 임시 테스트
 */
@SpringBootTest
class JwtTokenConfigTest {

    @Autowired
    private JwtTokenConfig jwtTokenConfig;

    @Test
    @DisplayName("token yml 설정값 확인")
    void jwtTokenConfigTest() {
        String secret = jwtTokenConfig.getSecret();
        long accessTime = jwtTokenConfig.getAccessTime();
        String authoritesKey = jwtTokenConfig.getAuthoritesKey();

        assertThat(secret).isEqualTo("b3JpX3RveV9wcm9qZWN0X3JlYWxfd29ybGRfamF2YV9zcHJpbmdfYm9vdF9zZWNyZXRfand0X3NhdWx0X2tleQo=");
        assertThat(accessTime).isEqualTo(1800000);
        assertThat(authoritesKey).isEqualTo("Authorization");
    }

}