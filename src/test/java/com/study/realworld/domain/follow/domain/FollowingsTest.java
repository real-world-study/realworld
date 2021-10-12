package com.study.realworld.domain.follow.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class FollowingsTest {

    @DisplayName("Following 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final Followings followings = new Followings();

        assertAll(
                () -> assertThat(followings).isNotNull(),
                () -> assertThat(followings).isExactlyInstanceOf(Followings.class)
        );
    }

}