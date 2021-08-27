package com.study.realworld.core.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mockStatic;

/**
 * @author JeongJoon Seo
 */
@ExtendWith(MockitoExtension.class)
class SecurityUtilTest {

    @Test
    void getAccessTokenTest() {
        final String accessToken = "accessToken";
        try (MockedStatic<SecurityUtil> securityUtilMockedStatic = mockStatic(SecurityUtil.class)) {
            securityUtilMockedStatic.when(SecurityUtil::getAccessToken).thenReturn(accessToken);
            assertThat(SecurityUtil.getAccessToken()).isEqualTo(accessToken);
        }
    }
}
