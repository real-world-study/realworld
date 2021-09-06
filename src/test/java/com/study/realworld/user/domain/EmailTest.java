package com.study.realworld.user.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.study.realworld.global.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class EmailTest {

    @Test
    void emailTest() {
        Email email = new Email();
    }

    @ParameterizedTest
    @ValueSource(strings = {"test1test.com", "test1@@test.com", "test@test..com"})
    @DisplayName("이메일 양식이 지켜지지 않은 Email는 validate된다.")
    void validEmailTest(String input) {

        // when & then
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new Email(input))
            .withMessageMatching(ErrorCode.INVALID_EMAIL_PATTERN.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "   "})
    @DisplayName("공백인 Email은 validate된다.")
    void blankEmailTest(String input) {

        // when & then
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new Email(input))
            .withMessageMatching(ErrorCode.INVALID_EMAIL_NULL.getMessage());
    }

    @Test
    @DisplayName("null인 Email은 validate된다.")
    void nullEmailTest() {

        // given
        String input = null;

        // when & given
        // when & then
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new Email(input))
            .withMessageMatching(ErrorCode.INVALID_EMAIL_NULL.getMessage());
    }

    @Test
    @DisplayName("equals hashCode 테스트")
    void emailEqualsHashCodeTest() {

        // given
        Email email = new Email("test@test.com");
        Email copyEmail = new Email("test@test.com");

        // when & then
        assertThat(email)
            .isEqualTo(copyEmail)
            .hasSameHashCodeAs(copyEmail);
    }

    @Test
    @DisplayName("toString 테스트")
    void emailToStringTest() {

        // given
        String input = "test@test.com";

        // when
        Email email = new Email(input);

        // then
        assertThat(email.toString()).isEqualTo(input);
    }

}
