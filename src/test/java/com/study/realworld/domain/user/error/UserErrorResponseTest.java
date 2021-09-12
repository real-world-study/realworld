package com.study.realworld.domain.user.error;

import com.study.realworld.global.config.error.testUtils.TestErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserErrorResponseTest {

    @DisplayName("UserErrorResponse 인스턴스 기본 생성자 테스트")
    @Test
    void default_construct_test() {
        final UserErrorResponse userErrorResponse = new UserErrorResponse();

        assertAll(
                () -> assertThat(userErrorResponse).isNotNull(),
                () -> assertThat(userErrorResponse).isExactlyInstanceOf(UserErrorResponse.class)
        );
    }

    @DisplayName("UserErrorResponse 인스턴스 생성자 테스트")
    @Test
    void construct_test() {
        final UserErrorResponse userErrorResponse = new UserErrorResponse(TestErrorCode.TEST);

        assertAll(
                () -> assertThat(userErrorResponse).isNotNull(),
                () -> assertThat(userErrorResponse).isExactlyInstanceOf(UserErrorResponse.class)
        );
    }

}