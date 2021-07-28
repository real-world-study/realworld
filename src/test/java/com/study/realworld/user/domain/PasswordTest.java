package com.study.realworld.user.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collection;

import static com.study.realworld.user.domain.Password.encode;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PasswordTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    private Validator validator;

    @BeforeEach
    void beforeEach() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    void passwordTest() {
        Password password = new Password();
    }

    @Test
    @DisplayName("패스워드 길이가 20이 넘으면 invalid 되어야 한다.")
    void passwordMaxSizeTest() {

        // given
        Password password = new Password("12345678901234567890123");

        // when
        Collection<ConstraintViolation<Password>> constraintViolations
            = validator.validate(password);

        // then
        assertEquals(1, constraintViolations.size());
        assertEquals("password length must be between 6 and 20 characters.",
            constraintViolations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("패스워드 길이가 6이 안되면 invalid 되어야 한다.")
    void passwordMinSizeTest() {

        // given
        Password password = new Password("12345");

        // when
        Collection<ConstraintViolation<Password>> constraintViolations
            = validator.validate(password);

        // then
        assertEquals(1, constraintViolations.size());
        assertEquals("password length must be between 6 and 20 characters.",
            constraintViolations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("패스워드에 null이 들어오면 invalid 되어야 한다.")
    void passwordNullTest() {

        // given
        Password password = new Password(null);

        // when
        Collection<ConstraintViolation<Password>> constraintViolations
            = validator.validate(password);

        // then
        assertEquals(1, constraintViolations.size());
        assertEquals("password must be provided.",
            constraintViolations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("패스워드에 빈 공백이 들어오면 invalid 되어야 한다.")
    void passwordBlankTest() {

        // given
        Password password = new Password("         ");

        // when
        Collection<ConstraintViolation<Password>> constraintViolations
            = validator.validate(password);

        // then
        assertEquals(1, constraintViolations.size());
        assertEquals("password must be provided.",
            constraintViolations.iterator().next().getMessage());
    }

    @Test
    @DisplayName("인코더에 패스워드를 넣으면 인코딩된 값이 나와야 한다.")
    void passwordEncodeTest() {

        // setup
        when(passwordEncoder.encode(any())).thenReturn("encoded_password");

        // given
        String input = "password";

        // when
        Password result = encode(input, passwordEncoder);

        // then
        assertThat(result.getPassword()).isEqualTo("encoded_password");
    }

    @Test
    @DisplayName("입력된 비밀번호가 존재하는 비밀번호와 같으면 true를 반환해야 한다.")
    void passwordMathckTest() {

        // setup
        when(passwordEncoder.matches("password", "encoded_password")).thenReturn(true);

        // given
        Password password = new Password("encoded_password");
        String input = "password";

        // when
        boolean result = password.matchPassword("password", passwordEncoder);

        // then
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("입력된 비밀번호가 존재하는 비밀번호와 다르면 false를 반환해야 한다.")
    void passwordDismatchTest() {

        // setup
        when(passwordEncoder.matches("password", "encoded_password")).thenReturn(false);

        // given
        Password password = new Password("encoded_password");
        String input = "password";

        // when
        boolean result = password.matchPassword("password", passwordEncoder);

        // then
        assertThat(result).isFalse();
    }

}
