package com.study.realworld.global.security.error;

import com.study.realworld.global.security.error.exception.UserDetailsNullPointerException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

class SecurityErrorCodeTest {

    @Test
    void values_test() {
        assertAll(
                () -> assertThat(SecurityErrorCode.values(new UserDetailsNullPointerException())).isNotNull()
        );
    }

    @Test
    void httpStatus_test() {
        assertAll(
                () -> assertThat(SecurityErrorCode.values(
                        new UserDetailsNullPointerException()).httpStatus()).isEqualTo(BAD_REQUEST)
        );
    }

    @Test
    void message_test() {
        assertAll(
                () -> assertThat(SecurityErrorCode.values(
                        new UserDetailsNullPointerException()).message()).isEqualTo("The request data is invalid")
        );
    }

}