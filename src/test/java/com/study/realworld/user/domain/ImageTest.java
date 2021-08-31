package com.study.realworld.user.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ImageTest {

    @Test
    void imageTest() {
        Image image = new Image();
    }

    @Test
    @DisplayName("equals hashCode 테스트")
    void imageEqualsHashCodeTest() {

        // given
        Image image = new Image("image");
        Image copyImage = new Image("image");

        // when & then
        assertThat(image)
            .isEqualTo(copyImage)
            .hasSameHashCodeAs(copyImage);
    }

    @Test
    @DisplayName("toString 테스트")
    void imageToStringTest() {

        // given
        String input = "image";

        // when
        Image image = new Image(input);

        // then
        assertThat(image.toString()).isEqualTo("image");
    }

}
