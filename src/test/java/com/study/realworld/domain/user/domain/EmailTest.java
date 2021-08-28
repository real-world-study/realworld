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

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class EmailTest {

    public static final String EMAIL = "test@tset.com";

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
        final String emailString = "test@test.com";
        final Email email = new Email(emailString);

        assertThat(email.email()).isEqualTo(emailString);
    }

    @DisplayName("Email 인스턴스 equals and hashcode 동등성 검증 테스트")
    @Test
    void equals_and_hashcode_test() {
        final String emailString = "test@test.com";
        final Email firstEmail = new Email(emailString);
        final Email secondEmail = new Email(emailString);

        assertAll(
                () -> assertThat(firstEmail).isEqualTo(secondEmail),
                () -> assertThat(firstEmail.hashCode()).isEqualTo(secondEmail.hashCode())
        );
    }

    @DisplayName("Email 인스턴스 toString 테스트")
    @Test
    void toString_test() {
        final String emailString = "test@test.com";
        final Email email = new Email(emailString);

        assertThat(email.toString()).isEqualTo(format("Email{email='%s'}", emailString));
    }

    @DisplayName("Email 인스턴스 @NotBlank 테스트")
    @Test
    void email_notBlank_test() {
        final Email email = new Email(Strings.EMPTY);
        final Set<ConstraintViolation<Email>> violations = validator.validate(email);

        assertAll(
                () -> assertThat(violations.size()).isEqualTo(1),
                () -> assertThat(violations.iterator().next().getMessage()).isEqualTo("Email must have not blank")
        );
    }

    @DisplayName("Email 인스턴스 @Email 테스트")
    @ValueSource(strings = {"test_test.com", "test@@test", "@test.com"})
    @ParameterizedTest
    void email_emailFormat_test(final String emailString) {
        final Email email = new Email(emailString);
        final Set<ConstraintViolation<Email>> violations = validator.validate(email);

        assertAll(
                () -> assertThat(violations.size()).isEqualTo(1),
                () -> assertThat(violations.iterator().next().getMessage()).isEqualTo("Invalid email address")
        );
    }

}