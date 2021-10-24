package com.study.realworld.article.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SlugTitleTest {

    @Test
    void slugTitleTest() {
        SlugTitle slugTitle = new SlugTitle();
    }

    @Test
    @DisplayName("equals hashCode 테스트")
    void slugTitleEqualsHashCodeTest() {

        // given
        Title title = Title.of("title");

        // when
        SlugTitle result = SlugTitle.of(title);

        // then
        assertThat(result)
            .isEqualTo(SlugTitle.of(title))
            .hasSameHashCodeAs(SlugTitle.of(title));
    }

}