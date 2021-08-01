package com.study.realworld.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BaseTimeEntityTest {

    @DisplayName("BaseTimeEntity 인스턴스 생성 테스트")
    @Test
    void constructor_test() {
        final BaseTimeEntity baseTimeEntity = new BaseTimeEntity(now(), now(), null);

        assertAll(
                () -> assertThat(baseTimeEntity).isNotNull(),
                () -> assertThat(baseTimeEntity).isExactlyInstanceOf(BaseTimeEntity.class)
        );
    }

}