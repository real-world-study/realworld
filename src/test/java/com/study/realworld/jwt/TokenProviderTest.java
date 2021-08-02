package com.study.realworld.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
class TokenProviderTest {

    @Mock
    private Authentication authentication;

    private TokenProvider tokenProvider;

    @BeforeEach
    void beforeEach() {
        tokenProvider = new TokenProvider("headerType", "issuer",
            "dGVzdF90ZXN0X3Rlc3RfdGVzdF90ZXN0X3Rlc3RfdGVzdF90ZXN0X3Rlc3RfdGVzdF90ZXN0X3Rlc3RfdGVzdF8K", 10L);
    }

    @Test
    @DisplayName("generateToken으로 토큰이 생성되어야 한다.")
    void generateTokenTest() {

        // setup & given
        when(authentication.getName()).thenReturn("username");

        // when
        String result = tokenProvider.generateToken(authentication);

        // then
        assertThat(result).isNotNull();
    }
}