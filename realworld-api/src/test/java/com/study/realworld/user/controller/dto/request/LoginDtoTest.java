package com.study.realworld.user.controller.dto.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.study.realworld.user.entity.User;

class LoginDtoTest {

    @DisplayName("create")
    @Test
    void create() {}

    @DisplayName("toEntity")
    @Test
    void toEntity() {
        final LoginDto loginDto = LoginDto.create("DolphaGo@test.net", "12345");
        final User user = loginDto.toEntity();
        assertAll(
                () -> assertThat(user.getEmail()).isEqualTo("DolphaGo@test.net"),
                () -> assertThat(user.getPassword()).isEqualTo("12345")
        );
    }
}
