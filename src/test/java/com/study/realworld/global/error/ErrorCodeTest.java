package com.study.realworld.global.error;

import com.study.realworld.global.error.testUtils.TestErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ErrorCodeTest {

    @DisplayName("ErrorCode 구현채 getter 태스트")
    @Test
    void getter_test() {
        final ErrorCode errorCode = TestErrorCode.TEST;

        assertAll(
                () -> assertThat(errorCode.httpStatus()).isEqualTo(HttpStatus.BAD_REQUEST),
                () -> assertThat(errorCode.message()).isEqualTo("test message")
        );
    }
}