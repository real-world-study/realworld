package com.study.realworld.user.controller.dto.request;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.study.realworld.user.entity.User;

class JoinDtoTest {

    @DisplayName("noArgsConstructor")
    @Test
    void noArgsConstructor() {
        JoinDto joinDto = new JoinDto();
        assertThat(joinDto).isInstanceOf(JoinDto.class);
    }

    @DisplayName("JoinDto")
    @Test
    void joinDtoTest() {

        final JoinDto joinDto = JoinDto.create("DolphaGo@test.net", "DolphaGo", "12345");

        assertAll(
                () -> assertThat(joinDto.getEmail()).isEqualTo("DolphaGo@test.net"),
                () -> assertThat(joinDto.getUsername()).isEqualTo("DolphaGo"),
                () -> assertThat(joinDto.getPassword()).isEqualTo("12345")
        );
    }

    @DisplayName("toEntity")
    @Test
    void toEntity() {
        final JoinDto joinDto = JoinDto.create("DolphaGo@test.net", "DolphaGo", "12345");
        String encodedPassword = "1q2w3e4r";
        final User user = joinDto.toEntity(encodedPassword);

        assertAll(
                () -> assertThat(user.getEmail()).isEqualTo("DolphaGo@test.net"),
                () -> assertThat(user.getUsername()).isEqualTo("DolphaGo"),
                () -> assertThat(user.getPassword()).isEqualTo(encodedPassword)
        );
    }
}
