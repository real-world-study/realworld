package com.study.realworld.domain.article.api.converter;

import com.study.realworld.domain.article.api.converter.ArticleSlugConverter.ArticleSlugToStringConverter;
import com.study.realworld.domain.article.api.converter.ArticleSlugConverter.StringToArticleSlugConverter;
import com.study.realworld.domain.article.domain.vo.ArticleSlug;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("게시글 슬러그 컨버터(ArticleSlugConverter)")
class ArticleSlugConverterTest {

    @ParameterizedTest(name = "입력값 : {0}")
    @ValueSource(strings = {"a", "ab", "abc"})
    void 문자열을_객체로_변환할_수_있다(String slugString) {
        final StringToArticleSlugConverter converter = new StringToArticleSlugConverter();
        final ArticleSlug articleSlug = converter.convert(slugString);

        assertThat(articleSlug.articleSlug()).isEqualTo(slugString);
    }

    @ParameterizedTest(name = "입력값 : {0}")
    @ValueSource(strings = {"a", "ab", "abc"})
    void 객체를_문자열로_변환할_수_있다(String slugString) {
        final ArticleSlug articleSlug = ArticleSlug.from(slugString);
        final ArticleSlugToStringConverter converter = new ArticleSlugToStringConverter();
        final String actual = converter.convert(articleSlug);

        assertThat(actual).isEqualTo(slugString);
    }
}
