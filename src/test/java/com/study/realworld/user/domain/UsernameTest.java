package com.study.realworld.user.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class UsernameTest {

    @Test
    void usernameTest() {
        Username username = new Username();
    }

    @Test
    @DisplayName("유저 네임은 null이 들어오면 invalid되어야 한다.")
    void usernameNullTest() {

        // given
        String input = null;

        // when & then
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new Username(input))
            .withMessageMatching("username must be provided.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "    "})
    @DisplayName("유저 네임은 빈칸이 들어오면 invalid되어야 한다.")
    void usernameNothingTest(String input) {

        // when & then
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new Username(input))
            .withMessageMatching("username must be provided.");
    }

    @Test
    @DisplayName("유저네임이 20글자가 넘어가면 invalid되어야 한다.")
    void usernameMaxSizeTest() {

        // given
        String input = "123456789012345678901";

        // when & then
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new Username(input))
            .withMessageMatching("username length must be less then 20 characters.");
    }

    @Test
    @DisplayName("유저네임은 한글, 숫자, 영어만 들어갈 수 있다.")
    void usernameValidTest() {

        // given
        String input = "123가믜힣abcABC";

        // when
        Username username = new Username(input);

        // then
        assertThat(username.toString()).isEqualTo(input);
    }

    @ParameterizedTest
    @ValueSource(strings = {"테스트1#", "@test", "test."})
    @DisplayName("유저네임에 한글, 숫자, 영어 말고 다른 값이 들어오면 invalid되어야한다.")
    void usernameInvalidTest(String input) {

        // when & then
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new Username(input))
            .withMessageMatching("usernmae must be provided by limited pattern.");
    }

    @Test
    @DisplayName("equals hashCode 테스트")
    void usernameEqualsHashCodeTest() {

        // given
        Username username = new Username("username");
        Username copyUsername = new Username("username");

        // when & then
        assertThat(username)
            .isEqualTo(copyUsername)
            .hasSameHashCodeAs(copyUsername);
    }

    @Test
    @DisplayName("toString 테스트")
    void usernameToStringTest() {

        // given
        String input = "username";

        // when
        Username username = new Username(input);

        // then
        assertThat(username.toString()).isEqualTo(input);
    }

}
