package com.study.realworld.domain.auth.infrastructure;

import com.study.realworld.domain.auth.dto.ResponseToken;
import com.study.realworld.domain.user.domain.User;
import com.study.realworld.global.config.security.jwt.JwtAuthentication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.TestPropertySource;

import java.util.Collections;

import static com.study.realworld.domain.user.domain.EmailTest.EMAIL;
import static com.study.realworld.domain.user.domain.PasswordTest.PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@TestPropertySource("classpath:application.yml")
class TokenProviderTest {

    private String testKey = "c2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQtc2lsdmVybmluZS10ZWNoLXNwcmluZy1ib290LWp3dC10dXRvcmlhbC1zZWNyZXQK";

    @DisplayName("TokenProvider 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final TokenProvider tokenProvider = new TokenProvider(testKey);
        assertAll(
                () -> assertThat(tokenProvider).isNotNull(),
                () -> assertThat(tokenProvider).isExactlyInstanceOf(TokenProvider.class)
        );
    }

    @DisplayName("TokenProvider 인스턴스 createToken() 테스트")
    @Test
    void createToken_test() {
        final TokenProvider tokenProvider = new TokenProvider(testKey);
        final SimpleGrantedAuthority authority = new SimpleGrantedAuthority(User.DEFAULT_AUTHORITY);
        final JwtAuthentication authentication = new JwtAuthentication(EMAIL, PASSWORD, Collections.singleton(authority));

        final ResponseToken token = tokenProvider.createToken(authentication);
        assertAll(
                () -> assertThat(token).isNotNull(),
                () -> assertThat(token).isInstanceOf(ResponseToken.class)
        );
    }


}