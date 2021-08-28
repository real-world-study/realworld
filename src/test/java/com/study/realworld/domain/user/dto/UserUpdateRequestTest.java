package com.study.realworld.domain.user.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserUpdateRequestTest {

    @DisplayName("UserUpdateRequest 기본 생성자 테스트")
    @Test
    void default_constructor_test() {
        final UserUpdateRequest userUpdateRequest = new UserUpdateRequest();

        assertAll(
                () -> assertThat(userUpdateRequest).isNotNull(),
                () -> assertThat(userUpdateRequest).isExactlyInstanceOf(UserUpdateRequest.class)
        );
    }

}