package com.study.realworld.domain.user.domain;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

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

    @DisplayName("Email 인스턴스 값 공백 검증 테스트")
    @Test
    void email_empty_test() {
        final Email email = new Email(Strings.EMPTY);
        final Set<ConstraintViolation<Email>> violations = validator.validate(email);

        assertAll(
                () -> assertThat(violations.size()).isGreaterThanOrEqualTo(1),
                () -> assertThat(violations.iterator().next().getMessage()).isEqualTo("Email must have not blank")
        );
    }

    @DisplayName("Email 인스턴스 값 이메일 형식 검증 테스트")
    @ValueSource(strings = {" ", "test_test.com", "test@@test", "@test.com"})
    @ParameterizedTest
    void email_format_test(final String emailString) {
        final Email email = new Email(emailString);
        final Set<ConstraintViolation<Email>> violations = validator.validate(email);

        assertAll(
                () -> assertThat(violations.size()).isGreaterThanOrEqualTo(1),
                () -> assertThat(violations.iterator().next().getMessage()).isEqualTo("Invalid email address")
        );
    }

}