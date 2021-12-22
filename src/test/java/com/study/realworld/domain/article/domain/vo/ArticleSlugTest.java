package com.study.realworld.domain.article.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("게시글 슬러그(ArticleSlug)")
class ArticleSlugTest {

    @ParameterizedTest(name = "입력값 : {0}")
    @ValueSource(strings = {"photo", "willy", "jhon", "kathy"})
    void 문자열로_객체를_생성할_수_있다(final String articleSlugString) {
        final ArticleSlug articleSlug = ArticleSlug.from(articleSlugString);

        assertAll(
                () -> assertThat(articleSlug).isNotNull(),
                () -> assertThat(articleSlug).isExactlyInstanceOf(ArticleSlug.class)
        );
    }

    @ParameterizedTest(name = "입력값 : {0}")
    @ValueSource(strings = {"photo", "willy", "jhon", "kathy"})
    void 값을_반환할_수_있다(final String articleSlugString) {
        final ArticleSlug articleSlug = ArticleSlug.from(articleSlugString);

        assertThat(articleSlug.articleSlug()).isEqualTo(articleSlugString);
    }

    @ParameterizedTest(name = "입력값 : {0}")
    @ValueSource(strings = {"photo", "willy", "jhon", "kathy"})
    void 동일_값_기준으로_생성시_동등하다(final String articleSlugString) {
        final ArticleSlug articleSlug = ArticleSlug.from(articleSlugString);
        final ArticleSlug other = ArticleSlug.from(articleSlugString);

        assertAll(
                () -> assertThat(articleSlug).isEqualTo(other),
                () -> assertThat(articleSlug).hasSameHashCodeAs(other)
        );
    }
}
