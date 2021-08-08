package com.study.realworld.domain.user.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {

    private static PasswordEncoder passwordEncoder;

    @BeforeAll
    void setUp() {
        passwordEncoder = new PasswordEncoder() {
            @Override
            public String encode(CharSequence plainTextPassword) {
                return BCrypt.hashpw(plainTextPassword.toString(),BCrypt.gensalt(8));
            }

            @Override
            public boolean matches(CharSequence plainTextPassword, String passwordInDatabase) {
                return BCrypt.checkpw(plainTextPassword.toString(),passwordInDatabase);
            }
        };
    }

    @DisplayName("Password 인스턴스 기본 생성자 테스트")
    @Test
    void default_constructor_test() {
        final Password password = new Password();

        assertAll(
                () -> assertThat(password).isNotNull(),
                () -> assertThat(password).isExactlyInstanceOf(Password.class)
        );
    }

    @DisplayName("Password 인스턴스 정적 팩터리 메서드 생성 테스트")
    @Test
    void static_factory_method_test() {
        final String passwordString = "password";
        final Password password = Password.createWithEncoder(passwordString, passwordEncoder);

        assertAll(
                () -> assertThat(password).isNotNull(),
                () -> assertThat(password).isExactlyInstanceOf(Password.class)
        );
    }

//    @DisplayName("Password 인스턴스 값이 비교 대상과 같은지 검증 테스트")
//    @Test
//    void checkPassword_test() {
//        final String passwordString = "password";
//        final String invalidPasswordString = "invalidPassword";
//        final Password password = Password.createWithEncoder(passwordString, passwordEncoder);
//
//        assertAll(
//                () -> assertThat(password.checkPassword(passwordString, passwordEncoder)).isTrue(),
//                () -> assertThat(password.checkPassword(invalidPasswordString, passwordEncoder)).isFalse()
//        );
//    }

}