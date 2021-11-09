package com.study.realworld.article.domain.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.study.realworld.tag.domain.Tag;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArticleContentTest {

    private Title title;
    private SlugTitle slugTitle;
    private Description description;
    private Body body;
    private List<Tag> tags;

    @BeforeEach
    void beforeEach() {
        title = Title.of("title");
        slugTitle = SlugTitle.of(title);
        description = Description.of("description");
        body = Body.of("body");
        tags = Arrays.asList(Tag.of("tag1"), Tag.of("tag2"));
    }

    @Test
    void articleContentTest() {
        ArticleContent articleContent = new ArticleContent();
    }

    @Test
    @DisplayName("title을 변경할 수 있다.")
    void changeTitleTest() {

        // given
        Title changeTitle = Title.of("title title");
        ArticleContent articleContent = ArticleContent.builder()
            .slugTitle(SlugTitle.of(title)).build();

        // when
        articleContent.changeTitle(changeTitle);

        // then
        assertAll(
            () -> assertThat(articleContent.title()).isEqualTo(changeTitle),
            () -> assertThat(articleContent.slug()).isEqualTo(Slug.of(changeTitle.titleToSlug()))
        );
    }

    @Test
    @DisplayName("description을 변경할 수 있다.")
    void changeDescriptionTest() {

        // given
        Description changeDescription = Description.of("new description");
        ArticleContent articleContent = ArticleContent.builder()
            .description(description).build();

        // when
        articleContent.changeDescription(changeDescription);

        // then
        assertThat(articleContent.description()).isEqualTo(changeDescription);
    }

    @Test
    @DisplayName("body를 변경할 수 있다.")
    void changeBodyTest() {

        // given
        Body changeBody = Body.of("new body");
        ArticleContent articleContent = ArticleContent.builder()
            .body(body).build();

        // when
        articleContent.changeBody(changeBody);

        // then
        assertThat(articleContent.body()).isEqualTo(changeBody);
    }

    @Test
    @DisplayName("builder test")
    void articleContentBuilderTest() {

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
            () -> assertThat(result.tags().stream().map(Tag::of).collect(Collectors.toList())).isEqualTo(tags)
        );
    }

    @Test
    @DisplayName("equals hashCode 테스트")
    void articleContentEqualsHashCodeTest() {

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