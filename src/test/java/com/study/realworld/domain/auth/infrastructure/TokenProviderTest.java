package com.study.realworld.domain.auth.infrastructure;

import com.study.realworld.domain.auth.dto.ResponseToken;
import com.study.realworld.domain.user.domain.User;
import com.study.realworld.global.config.security.jwt.JwtAuthentication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

import static com.study.realworld.domain.user.domain.EmailTest.EMAIL;
import static com.study.realworld.domain.user.domain.PasswordTest.PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class TokenProviderTest {

    // spring-boot-security-jwt-tutorial-jiwoon-spring-boot-security-jwt-tutorial
    private static final String TEST_KEY = "c3ByaW5nLWJvb3Qtc2VjdXJpdHktand0LXR1dG9yaWFsLWppd29vbi1zcHJpbmctYm9vdC1zZWN1cml0eS1qd3QtdHV0b3JpYWwK";
    private static final String TEST_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqYWtlQGpha2UuamFrZSIsImV4cCI6MTYyOTk4OTU2OH0.5LJXRjabGDwbRWh2p-jVOSvvPbkCvUVVKKViRD5QkN4jVf--QMJs4q75PRJbLonadseD6hMeptOdf1RhY5ZCZA";

    @DisplayName("TokenProvider 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final TokenProvider tokenProvider = new TokenProvider(TEST_KEY);
        assertAll(
                () -> assertThat(tokenProvider).isNotNull(),
                () -> assertThat(tokenProvider).isExactlyInstanceOf(TokenProvider.class)
        );
    }

    @DisplayName("TokenProvider 인스턴스 createToken() 테스트")
    @Test
    void createToken_test() {
        final TokenProvider tokenProvider = new TokenProvider(TEST_KEY);
        final SimpleGrantedAuthority authority = new SimpleGrantedAuthority(User.DEFAULT_AUTHORITY);
        final JwtAuthentication authentication = new JwtAuthentication(EMAIL, PASSWORD, Collections.singleton(authority));

        final ResponseToken token = tokenProvider.createToken(authentication);
        assertAll(
                () -> assertThat(token).isNotNull(),
                () -> assertThat(token).isInstanceOf(ResponseToken.class)
        );
    }

    @DisplayName("TokenProvider 인스턴스 validateToken() 테스트")
    @Test
    void validateToken_test() {
        final TokenProvider tokenProvider = new TokenProvider(TEST_KEY);

        assertAll(
                () -> assertThat(tokenProvider.validateToken(TEST_TOKEN)).isTrue(),
                () -> assertThat(tokenProvider.validateToken("failToken")).isFalse()
        );
    }

}