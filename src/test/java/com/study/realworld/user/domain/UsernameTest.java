package com.study.realworld.user.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class UsernameTest {

    private Validator validator;

    @BeforeEach
    void beforeEach() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void usernameTest() {
        Username username = new Username();
    }

    @Test
    @DisplayName("유저 네임은 null이 들어오면 invalid되어야 한다.")
    void usernameNullTest() {

        // given
        Username username = new Username(null);

        // when
        Collection<ConstraintViolation<Username>> constraintViolations
            = validator.validate(username);

        // then
        assertEquals(1, constraintViolations.size());
        assertEquals("username must be provided.",
            constraintViolations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("유저 네임은 빈칸이 들어오면 invalid되어야 한다.")
    void usernameNothingTest() {

        // given
        Username username = new Username("");

        // when
        Collection<ConstraintViolation<Username>> constraintViolations
            = validator.validate(username);

        // then
        assertEquals(1, constraintViolations.size());
        assertEquals("username must be provided.",
            constraintViolations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("유저 네임은 공백이 들어오면 invalid되어야 한다.")
    void usernameBlankTest() {

        // given
        Username username = new Username(" ");

        // when
        Collection<ConstraintViolation<Username>> constraintViolations
            = validator.validate(username);

        // then
        assertEquals(2, constraintViolations.size());
    }

    @Test
    @DisplayName("유저네임이 20글자가 넘어가면 invalid되어야 한다.")
    void usernameMaxSizeTest() {

        // given
        Username username = new Username("123456789012345678901");

        // when
        Collection<ConstraintViolation<Username>> constraintViolations
            = validator.validate(username);

        // then
        assertEquals(1, constraintViolations.size());
        assertEquals("username length must be less than 20 characters.",
            constraintViolations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("유저네임은 한글, 숫자, 영어만 들어갈 수 있다.")
    void usernameValidTest() {

        // given
        Username username = new Username("123가믜힣abcABC");

        // when
        Collection<ConstraintViolation<Username>> constraintViolations
            = validator.validate(username);

        // then
        assertEquals(0, constraintViolations.size());
    }

    @ParameterizedTest
    @ValueSource(strings = {"테스트1#", "@test", "test."})
    @DisplayName("유저네임에 한글, 숫자, 영어 말고 다른 값이 들어오면 invalid되어야한다.")
    void usernameInvalidTest(String input) {

        // given
        Username username = new Username(input);

        // when
        Collection<ConstraintViolation<Username>> constraintViolations
            = validator.validate(username);

        // then
        assertEquals(1, constraintViolations.size());
        assertEquals("Invalid username name", constraintViolations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("equals hashCode 테스트")
    void usernameEqualsHashCodeTest() {

        // given
        Username username = new Username("test@test.com");
        Username copyUsername = new Username("test@test.com");

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
