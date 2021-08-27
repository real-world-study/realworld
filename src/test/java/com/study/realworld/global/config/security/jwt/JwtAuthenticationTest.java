package com.study.realworld.global.config.security.jwt;

import com.study.realworld.domain.user.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.Set;

import static com.study.realworld.domain.user.domain.BioTest.BIO;
import static com.study.realworld.domain.user.domain.EmailTest.EMAIL;
import static com.study.realworld.domain.user.domain.ImageTest.IMAGE;
import static com.study.realworld.domain.user.domain.NameTest.USERNAME;
import static com.study.realworld.domain.user.domain.PasswordTest.PASSWORD;
import static com.study.realworld.domain.user.domain.User.DEFAULT_AUTHORITY;
import static com.study.realworld.domain.user.domain.UserTest.userBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class JwtAuthenticationTest {

    @DisplayName("JwtAuthentication 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final SimpleGrantedAuthority authority = new SimpleGrantedAuthority(DEFAULT_AUTHORITY);
        final Set<SimpleGrantedAuthority> singleton = Collections.singleton(authority);
        final JwtAuthentication jwtAuthentication = new JwtAuthentication(new Email(EMAIL), new Password(PASSWORD), singleton);

        assertAll(
                () -> assertThat(jwtAuthentication).isNotNull(),
                () -> assertThat(jwtAuthentication).isInstanceOf(Authentication.class),
                () -> assertThat(jwtAuthentication).isExactlyInstanceOf(JwtAuthentication.class)
        );
    }

    @DisplayName("JwtAuthentication 인스턴스 initAuthentication() 정적 팩토리 메서드 테스트")
    @Test
    void static_factory_method_initAuthentication_test() {
        final String token = "token";
        final JwtAuthentication jwtAuthentication = JwtAuthentication.initAuthentication(token);

        assertAll(
                () -> assertThat(jwtAuthentication).isNotNull(),
                () -> assertThat(jwtAuthentication).isInstanceOf(Authentication.class),
                () -> assertThat(jwtAuthentication).isExactlyInstanceOf(JwtAuthentication.class)
        );
    }

    @DisplayName("JwtAuthentication 인스턴스 ofUserDetails() 정적 팩토리 메서드 테스트")
    @Test
    void static_factory_method_ofUserDetails_test() {
        final User user = userBuilder(new Email(EMAIL), new Name(USERNAME), new Password(PASSWORD), new Bio(BIO), new Image(IMAGE));
        final JwtAuthentication jwtAuthentication = JwtAuthentication.ofUser(user);

        assertAll(
                () -> assertThat(jwtAuthentication).isNotNull(),
                () -> assertThat(jwtAuthentication).isInstanceOf(Authentication.class),
                () -> assertThat(jwtAuthentication).isExactlyInstanceOf(JwtAuthentication.class)
        );
    }

    @DisplayName("JwtAuthentication 인스턴스 getter 기능 테스트")
    @Test
    void getter_test() {
        final User user = userBuilder(new Email(EMAIL), new Name(USERNAME), new Password(PASSWORD), new Bio(BIO), new Image(IMAGE));
        final JwtAuthentication jwtAuthentication = JwtAuthentication.ofUser(user);

        assertAll(
                () -> assertThat(jwtAuthentication.getPrincipal()).isEqualTo(EMAIL),
                () -> assertThat(jwtAuthentication.getCredentials()).isEqualTo(PASSWORD)
        );
    }

}