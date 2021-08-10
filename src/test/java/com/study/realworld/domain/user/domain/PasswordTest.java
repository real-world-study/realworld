package com.study.realworld.domain.user.domain;

import com.study.realworld.domain.user.domain.testUtil.TestPasswordEncoder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class PasswordTest {

    public static final String PASSWORD = "password";
    public static PasswordEncoder PASSWORD_ENCODER = new TestPasswordEncoder();

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
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
        final Password password = new Password(passwordString);

        assertAll(
                () -> assertThat(password).isNotNull(),
                () -> assertThat(password).isExactlyInstanceOf(Password.class)
        );
    }

    @DisplayName("Password 인스턴스 값이 비교 대상과 같은지 검증 테스트")
    @Test
    void checkPassword_test() {
        final String passwordString = "password";
        final String invalidPasswordString = "invalidPassword";
        final Password password = Password.encode(new Password(passwordString), PASSWORD_ENCODER);

        assertAll(
                () -> assertThat(password.checkPasswordWithEncoder(passwordString, PASSWORD_ENCODER)).isTrue(),
                () -> assertThat(password.checkPasswordWithEncoder(invalidPasswordString, PASSWORD_ENCODER)).isFalse()
        );
    }

    @DisplayName("Password 인스턴스 값 공백 검증 테스트")
    @Test
    void password_blank_test() {
        final String passwordString = "    ";
        final Password password = new Password(passwordString);

        final Set<ConstraintViolation<Password>> violations = validator.validate(password);
        assertAll(
                () -> assertThat(violations.size()).isEqualTo(1),
                () -> assertThat(violations.iterator().next().getMessage()).isEqualTo("Password must have not blank")
        );
    }

}