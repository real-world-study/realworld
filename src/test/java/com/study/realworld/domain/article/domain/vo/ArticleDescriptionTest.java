package com.study.realworld.domain.article.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("게시글 설명(ArticleDescription)")
class ArticleDescriptionTest {

    @ParameterizedTest(name = "입력값 : {0}")
    @ValueSource(strings = {"photo", "willy", "jhon", "kathy"})
    void 문자열로_객체를_생성할_수_있다(final String articleDescriptionString) {
        final ArticleDescription articleDescription = ArticleDescription.from(articleDescriptionString);

        assertAll(
                () -> assertThat(articleDescription).isNotNull(),
                () -> assertThat(articleDescription).isExactlyInstanceOf(ArticleDescription.class)
        );
    }

    @ParameterizedTest(name = "입력값 : {0}")
    @ValueSource(strings = {"photo", "willy", "jhon", "kathy"})
    void 값을_반환할_수_있다(final String articleDescriptionString) {
        final ArticleDescription articleDescription = ArticleDescription.from(articleDescriptionString);

        assertThat(articleDescription.articleDescription()).isEqualTo(articleDescriptionString);
    }

    @ParameterizedTest(name = "입력값 : {0}")
    @ValueSource(strings = {"photo", "willy", "jhon", "kathy"})
    void 동일_값_기준으로_생성시_동등하다(final String articleDescriptionString) {
        final ArticleDescription articleDescription = ArticleDescription.from(articleDescriptionString);
        final ArticleDescription other = ArticleDescription.from(articleDescriptionString);

        assertAll(
                () -> assertThat(articleDescription).isEqualTo(other),
                () -> assertThat(articleDescription).hasSameHashCodeAs(other)
        );
    }
}
