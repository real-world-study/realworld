package com.study.realworld.domain.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class NameTest {

    @DisplayName("Name 인스턴스 기본 생성자 테스트")
    @Test
    void default_constructor_test() {
        final Name name = new Name();

        assertAll(
                () -> assertThat(name).isNotNull(),
                () -> assertThat(name).isExactlyInstanceOf(Name.class)
        );
    }

}