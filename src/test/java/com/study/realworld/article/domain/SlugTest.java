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