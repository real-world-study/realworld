package com.study.realworld.domain.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ImageTest {

    @DisplayName("Image 인스턴스 기본 생성자 테스트")
    @Test
    void constructor_test() {
        final Image image = new Image();

        assertAll(
                () -> assertThat(image).isNotNull(),
                () -> assertThat(image).isExactlyInstanceOf(Email.class)
        );
    }

}