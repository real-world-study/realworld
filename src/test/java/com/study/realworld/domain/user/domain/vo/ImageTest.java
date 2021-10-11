package com.study.realworld.domain.user.domain.vo;

import com.study.realworld.domain.user.domain.vo.Image;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class ImageTest {

    public static final String IMAGE = "image";

    @DisplayName("Image 인스턴스 기본 생성자 테스트")
    @Test
    void default_constructor_test() {
        final Image image = new Image();

        assertAll(
                () -> assertThat(image).isNotNull(),
                () -> assertThat(image).isExactlyInstanceOf(Image.class)
        );
    }

    @DisplayName("Image 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final String imagePath = "imagePath";
        final Image image = new Image(imagePath);

        assertAll(
                () -> assertThat(image).isNotNull(),
                () -> assertThat(image).isExactlyInstanceOf(Image.class)
        );
    }

    @DisplayName("Image 인스턴스 getter 기능 테스트")
    @Test
    void getter_test() {
        final String imagePath = "imagePath";
        final Image image = new Image(imagePath);

        assertThat(image.path()).isEqualTo(imagePath);
    }

    @DisplayName("Image 인스턴스 equals and hashcode 동등성 검증 테스트")
    @Test
    void equals_and_hashcode_test() {
        final String imagePath = "imagePath";
        final Image firstImage = new Image(imagePath);
        final Image secondImage = new Image(imagePath);

        assertAll(
                () -> assertThat(firstImage).isEqualTo(secondImage),
                () -> assertThat(firstImage.hashCode()).isEqualTo(firstImage.hashCode())
        );
    }

    @DisplayName("Image 인스턴스 toString 테스트")
    @Test
    void toString_test() {
        final String imagePath = "imagePath";
        final Image image = new Image(imagePath);

        assertThat(image.toString()).isEqualTo(String.format("Image{path='%s'}", imagePath));
    }

}