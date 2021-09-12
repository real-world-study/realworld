package com.study.realworld.domain.user.error;

import com.study.realworld.domain.user.error.exception.DuplicatedEmailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.study.realworld.domain.user.domain.EmailTest.EMAIL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserErrorCodeTest {

    @DisplayName("UserErrorCode 열거형 생성 테스트")
    @Test
    void enum_create_test() {
        final UserErrorCode emailDuplication = UserErrorCode.EMAIL_DUPLICATION;
        assertThat(emailDuplication).isNotNull();
    }

    @DisplayName("UserErrorCode 열거형 httpStatus() 테스트")
    @Test
    void httpStatus_test() {
        assertThat(UserErrorCode.EMAIL_DUPLICATION.httpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @DisplayName("UserErrorCode 열거형 message() 테스트")
    @Test
    void message_test() {
        assertThat(UserErrorCode.EMAIL_DUPLICATION.message()).isEqualTo("Email is Duplication");
    }

    @DisplayName("UserErrorCode values() 테스트")
    @Test
    void values_test() {
        final UserErrorCode userErrorCode = UserErrorCode.values(new DuplicatedEmailException(EMAIL));

        assertAll(
                () -> assertThat(userErrorCode.httpStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(UserErrorCode.EMAIL_DUPLICATION.message()).isEqualTo("Email is Duplication")
        );
    }

}