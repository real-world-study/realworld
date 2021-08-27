package com.study.realworld.global.config.security.jwt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtFilterTest {

    @Mock private JwtProviderManager jwtProviderManager;
    @InjectMocks private JwtFilter jwtFilter;

    @DisplayName("JwtFilter 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final JwtFilter jwtFilter = new JwtFilter(jwtProviderManager);

        assertAll(
                () -> assertThat(jwtFilter).isNotNull(),
                () -> assertThat(jwtFilter).isExactlyInstanceOf(JwtFilter.class)
        );
    }

}