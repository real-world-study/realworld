package com.study.realworld.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BaseTimeEntityTest {

    @DisplayName("BaseTimeEntity 인스턴스 생성 테스트")
    @Test
    void constructor_test() {
        final LocalDateTime now = now();
        final BaseTimeEntity baseTimeEntity = new BaseTimeEntity();

        assertAll(
                () -> assertThat(baseTimeEntity).isNotNull(),
                () -> assertThat(baseTimeEntity).isExactlyInstanceOf(BaseTimeEntity.class)
        );
    }

    @DisplayName("BaseTimeEntity 인스턴스 삭제 값 변경 테스트")
    @Test
    void deletedAt_test() {
        final LocalDateTime now = now();
        final BaseTimeEntity baseTimeEntity = new BaseTimeEntity();
        final LocalDateTime deletedAt = now().plusMinutes(1);

        ReflectionTestUtils.setField(baseTimeEntity, "createdAt", now);
        ReflectionTestUtils.setField(baseTimeEntity, "updatedAt", now);
        baseTimeEntity.recordDeletedTime(deletedAt);

        assertAll(
                () -> assertThat(baseTimeEntity.deletedAt()).isEqualTo(deletedAt),
                () -> assertThat(baseTimeEntity.deletedAt()).isAfter(now)
        );
    }
}