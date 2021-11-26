package com.study.realworld.domain.article.domain.persist;

import com.study.realworld.domain.article.domain.vo.ArticleBody;
import com.study.realworld.domain.article.domain.vo.ArticleDescription;
import com.study.realworld.domain.article.domain.vo.ArticleSlug;
import com.study.realworld.domain.article.domain.vo.ArticleTitle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static com.study.realworld.domain.user.domain.persist.UserTest.testDefaultUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("게시글(Article)")
public class ArticleTest {

    @Test
    void 게시글_관련_요소를_통해_객체_생성이_가능하다() {
        final Article article = testArticle();

        assertAll(
                () -> assertThat(article).isNotNull(),
                () -> assertThat(article).isExactlyInstanceOf(Article.class)
        );
    }

    @Test
    void 식별자가_값으면_동일한_객체이다() {
        final Article article = testArticle();
        final Article other = testArticle();

        ReflectionTestUtils.setField(article, "articleId", 1L);
        ReflectionTestUtils.setField(other, "articleId", 1L);

        assertAll(
                () -> assertThat(article).isEqualTo(other),
                () -> assertThat(article).hasSameHashCodeAs(other)
        );
    }

    public static Article testArticle() {
        return Article.builder()
                .articleSlug(ArticleSlug.from("how-to-train-your-dragon"))
                .articleTitle(ArticleTitle.from("how to train your dragon"))
                .articleDescription(ArticleDescription.from("Ever wonder how?"))
                .articleBody(ArticleBody.from("It takes a Jacobian"))
                .author(testDefaultUser())
                .build();
    }
}
