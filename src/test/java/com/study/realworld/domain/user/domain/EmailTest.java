package com.study.realworld.domain.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @DisplayName("Email 인스턴스 기본 생성자 테스트")
    @Test
    void default_constructor_test() {
        final Email email = new Email();

        assertAll(
                () -> assertThat(email).isNotNull(),
                () -> assertThat(email).isExactlyInstanceOf(Email.class)
        );
    }

    @DisplayName("Email 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final String emailString = "kwj1270@naver.com";
        final Email email = new Email(emailString);

        assertAll(
                () -> assertThat(email).isNotNull(),
                () -> assertThat(email).isExactlyInstanceOf(Email.class)
        );
    }

    @DisplayName("Email 인스턴스 getter 테스트")
    @Test
    void getter_test() {
        final String emailString = "kwj1270@naver.com";
        final Email email = new Email(emailString);

        assertThat(email.email()).isEqualTo(emailString);
    }
}