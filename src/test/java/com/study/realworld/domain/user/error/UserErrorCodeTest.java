package com.study.realworld.domain.user.error;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserErrorCodeTest {

    @DisplayName("UserErrorCode 열거형 생성 테스트")
    @Test
    void enum_create_test() {
        assertThat(UserErrorCode.EMAIL_DUPLICATION).isNotNull();
    }

}