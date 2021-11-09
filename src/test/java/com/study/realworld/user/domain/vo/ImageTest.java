package com.study.realworld.user.domain.vo;

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
        Image image = Image.of("image");
        Image copyImage = Image.of("image");

        // when & then
        assertThat(image)
            .isEqualTo(copyImage)
            .hasSameHashCodeAs(copyImage);
    }

}
