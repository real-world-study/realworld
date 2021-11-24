package com.study.realworld.domain.article.domain.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("게시글(Article)")
class ArticleTest {

    @Test
    void 게시글_관련_요소를_통해_객체_생성이_가능하다() {
        final Article article = testArticle();

        assertAll(
                () -> assertThat(article).isNotNull(),
                () -> assertThat(article).isExactlyInstanceOf(Article.class)
        );
    }

    public static Article testArticle() {
        return Article.builder()
                .slug("how-to-train-your-dragon")
                .title("how to train your dragon")
                .description("Ever wonder how?")
                .body("It takes a Jacobian")
                .build();
    }
}
