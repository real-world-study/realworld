package com.study.realworld.article.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.study.realworld.tag.domain.Tag;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArticleContentTest {

    @Test
    void articleContentTest() {
        ArticleContent articleContent = new ArticleContent();
    }

    @Test
    @DisplayName("builder test")
    void articleContentBuilderTest() {

        // given
        Title title = Title.of("title");
        SlugTitle slugTitle = SlugTitle.of(title);
        Description description = Description.of("description");
        Body body = Body.of("body");
        List<Tag> tags = Arrays.asList(Tag.of("tag1"), Tag.of("tag2"));

        // when
        ArticleContent result = ArticleContent.builder()
            .slugTitle(slugTitle)
            .description(description)
            .body(body)
            .tags(tags)
            .build();

        // then
        assertAll(
            () -> assertThat(result.slug()).isEqualTo(slugTitle.slug()),
            () -> assertThat(result.title()).isEqualTo(title),
            () -> assertThat(result.description()).isEqualTo(description),
            () -> assertThat(result.body()).isEqualTo(body),
            () -> assertThat(result.tags()).isEqualTo(tags)
        );
    }

    @Test
    @DisplayName("equals hashCode 테스트")
    void articleContentEqualsHashCodeTest() {

        // given
        Title title = Title.of("title");
        SlugTitle slugTitle = SlugTitle.of(title);
        Description description = Description.of("description");
        Body body = Body.of("body");
        List<Tag> tags = Arrays.asList(Tag.of("tag1"), Tag.of("tag2"));

        // when
        ArticleContent result = ArticleContent.builder()
            .slugTitle(slugTitle)
            .description(description)
            .body(body)
            .tags(tags)
            .build();

        // then
        assertThat(result)
            .isEqualTo(ArticleContent.builder()
                .slugTitle(slugTitle)
                .description(description)
                .body(body)
                .tags(tags)
                .build())
            .hasSameHashCodeAs(ArticleContent.builder()
                .slugTitle(slugTitle)
                .description(description)
                .body(body)
                .tags(tags)
                .build());
    }

}