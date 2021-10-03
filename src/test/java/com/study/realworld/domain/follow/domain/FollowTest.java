package com.study.realworld.domain.follow.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FollowTest {

    @DisplayName("Follow 인스턴스 기본 생성자 테스트")
    @Test
    void constructor_test() {
        final Follow follow = new Follow();

        assertAll(
                () -> assertThat(follow).isNotNull(),
                () -> assertThat(follow).isExactlyInstanceOf(Follow.class)
        );
    }


}