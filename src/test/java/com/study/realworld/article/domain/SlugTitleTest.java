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
    @DisplayName("SlugTitle을 받아 변경할 수 있다.")
    void changeTitleTest() {

        // given
        SlugTitle slugTitle = SlugTitle.of(Title.of("title title title"));
        Title title = Title.of("title title");

        SlugTitle expected = SlugTitle.of(title);

        // when
        slugTitle.changeTitle(title);

        // then
        assertThat(slugTitle).isEqualTo(expected);
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