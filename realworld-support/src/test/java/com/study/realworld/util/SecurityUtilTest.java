package com.study.realworld.util;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SecurityUtilTest {
    @Test
    void static_method_mocking() {
        try(final MockedStatic<SecurityUtil> securityUtilMockedStatic = Mockito.mockStatic(SecurityUtil.class)){
            securityUtilMockedStatic.when(SecurityUtil::getCurrentUserToken).thenReturn("userToken");
            assertThat(SecurityUtil.getCurrentUserToken()).isEqualTo("userToken");
        }
    }
}
