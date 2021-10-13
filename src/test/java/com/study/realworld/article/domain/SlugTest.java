package com.study.realworld.article.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.study.realworld.global.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class SlugTest {

    @Test
    void slugTest() {
        Slug slug = new Slug();
    }

    @Test
    @DisplayName("slug 길이가 50이 넘을 경우 exception이 발생되어야 한다.")
    void slugLengthExceptionTest() {

        // given
        StringBuilder sb = new StringBuilder();
        String plus = "A";
        for (int i=1; i<=51; i++){
            sb.append(plus);
        }
        String input = sb.toString();

        // when & then
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> Slug.of(input))
            .withMessageMatching(ErrorCode.INVALID_SLUG_LENGTH.getMessage());
    }

    @NullAndEmptySource
    @ParameterizedTest
    @DisplayName("slug가 빈값이 들어올 경우 exception이 발생해야 한다.")
    void slugNullAndEmptyExceptionTest(String input) {

        // when & then
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> Slug.of(input))
            .withMessageMatching(ErrorCode.INVALID_SLUG_NULL.getMessage());
    }

    @Test
    @DisplayName("Title 객체를 받아서 Slug 객체를 생성할 수 있다.")
    void createSlugByTitleTest() {

        // given
        String input = "How to train% your   dragon!test";
        Title title = Title.of(input);

        Slug expected = Slug.of("how-to-train-your-dragon-test");

        // when
        Slug result = Slug.of(title);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("equals hashCode 테스트")
    void slugEqualsHashCodeTest() {

        // given
        String input = "slug";

        // when
        Slug result = Slug.of(input);

        // then
        assertThat(result)
            .isEqualTo(Slug.of(input))
            .hasSameHashCodeAs(Slug.of(input));
    }

}