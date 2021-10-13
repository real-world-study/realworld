package com.study.realworld.article.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SlugTest {

    @Test
    void slugTest() {
        Slug slug = new Slug();
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