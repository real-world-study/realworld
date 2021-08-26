package com.study.realworld.global.config.security.jwt;

import com.study.realworld.domain.user.domain.Email;
import com.study.realworld.domain.user.domain.Password;
import com.study.realworld.domain.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.Set;

import static com.study.realworld.domain.user.domain.EmailTest.EMAIL;
import static com.study.realworld.domain.user.domain.PasswordTest.PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class JwtAuthenticationTest {

    @DisplayName("JwtAuthentication 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final SimpleGrantedAuthority authority = new SimpleGrantedAuthority(User.DEFAULT_AUTHORITY);
        final Set<SimpleGrantedAuthority> singleton = Collections.singleton(authority);
        final JwtAuthentication jwtAuthentication = new JwtAuthentication(new Email(EMAIL), new Password(PASSWORD), singleton);

        assertAll(
                () -> assertThat(jwtAuthentication).isNotNull(),
                () -> assertThat(jwtAuthentication).isInstanceOf(Authentication.class),
                () -> assertThat(jwtAuthentication).isExactlyInstanceOf(JwtAuthentication.class)
        );
    }

}