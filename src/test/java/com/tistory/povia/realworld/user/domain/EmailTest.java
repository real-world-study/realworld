package com.tistory.povia.realworld.user.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EmailTest {

    @Test
    @DisplayName("이메일은 빈 칸, null 일 수 없음")
    void blankTest() {
        assertThatThrownBy(() -> new Email(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("");
    }

    @Test
    @DisplayName("이메일은 xxx@xxx.xxx의 규격이어야 할 것")
    void emailFormatTest() {
        assertThatThrownBy(() -> new Email("test@test"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("");
    }
}
