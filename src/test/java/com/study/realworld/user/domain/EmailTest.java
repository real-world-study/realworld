package com.study.realworld.user.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    private Validator validator;

    @BeforeEach
    void beforeEach() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void emailTest() {
        Email email = new Email();
    }

    @ParameterizedTest
    @ValueSource(strings = {" ", "test1test.com", "test1@@test.com", "test@test..com"})
    @DisplayName("이메일 양식이 지켜지지 않은 Email는 validate된다.")
    void validEmailTest(String input) {

        // given
        Email email = new Email(input);

        // when
        Collection<ConstraintViolation<Email>> constraintViolations
                = validator.validate(email);

        // then
        assertEquals(1, constraintViolations.size());
        assertEquals("Invalid email address", constraintViolations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("공백인 Email은 validate된다.")
    void blankEmailTest() {

        // given
        Email email = new Email("");

        // when
        Collection<ConstraintViolation<Email>> constraintViolations
                = validator.validate(email);

        // then
        assertEquals(1, constraintViolations.size());
        assertEquals("address must be provided.", constraintViolations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("null인 Email은 validate된다.")
    void nullEmailTest() {

        // given
        Email email = new Email(null);

        // when
        Collection<ConstraintViolation<Email>> constraintViolations
                = validator.validate(email);

        // then
        assertEquals(1, constraintViolations.size());
        assertEquals("address must be provided.", constraintViolations.iterator().next().getMessage());
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
