package com.study.realworld.user.domain;

import static com.study.realworld.user.domain.Password.encode;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.study.realworld.global.error.ErrorCode;
import com.study.realworld.global.error.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class PasswordTest {

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    void passwordTest() {
        Password password = new Password();
    }

    @Test
    @DisplayName("패스워드 길이가 20이 넘으면 invalid 되어야 한다.")
    void passwordMaxSizeTest() {

        // when & then
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new Password("12345678901234567890123"))
            .withMessageMatching("password length must be between 6 and 20 characters.");
    }

    @Test
    @DisplayName("패스워드 길이가 6이 안되면 invalid 되어야 한다.")
    void passwordMinSizeTest() {

        // when & then
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new Password("12345"))
            .withMessageMatching("password length must be between 6 and 20 characters.");
    }

    @Test
    @DisplayName("패스워드에 null이 들어오면 invalid 되어야 한다.")
    void passwordNullTest() {

        // when & then
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new Password(null))
            .withMessageMatching("password must be provided.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "    "})
    @DisplayName("패스워드에 빈 공백이 들어오면 invalid 되어야 한다.")
    void passwordBlankTest(String input) {

        // when & then
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> new Password(input))
            .withMessageMatching("password must be provided.");
    }

    @Test
    @DisplayName("인코더에 패스워드를 넣으면 인코딩된 값이 나와야 한다.")
    void passwordEncodeTest() {

        // setup
        when(passwordEncoder.encode(any())).thenReturn("encoded_password");

        // given
        Password password = new Password("password");

        // when
        Password result = encode(password, passwordEncoder);

        // then
        assertThat(result.password()).isEqualTo("encoded_password");
    }

    @Test
    @DisplayName("입력된 비밀번호가 존재하는 비밀번호와 같으면 Exception이 반환되지 않아야 한다.")
    void passwordMathckTest() {

        // setup
        when(passwordEncoder.matches("password", "encoded_password")).thenReturn(true);

        // given
        Password password = new Password("encoded_password");
        String input = "password";

        // when & then
        assertDoesNotThrow(() -> password.matchPassword(new Password("password"), passwordEncoder));
    }

    @Test
    @DisplayName("입력된 비밀번호가 존재하는 비밀번호와 다르면 Excpetion을 반환해야 한다.")
    void passwordDismatchTest() {

        // setup
        when(passwordEncoder.matches("password", "encoded_password")).thenReturn(false);

        // given
        Password password = new Password("encoded_password");
        String input = "password";

        // when & then
        assertThatExceptionOfType(BusinessException.class)
            .isThrownBy(() -> password.matchPassword(new Password("password"), passwordEncoder))
            .withMessageMatching(ErrorCode.PASSWORD_DISMATCH.getMessage());
    }

}
