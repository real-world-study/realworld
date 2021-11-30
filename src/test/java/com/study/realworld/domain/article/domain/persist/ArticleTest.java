package com.study.realworld.domain.article.domain.persist;

import com.study.realworld.domain.article.util.TestSlugStrategy;
import com.study.realworld.domain.user.domain.persist.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static com.study.realworld.domain.article.util.ArticleFixture.*;
import static com.study.realworld.domain.user.util.UserFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("게시글(Article)")
public class ArticleTest {

    @Test
    void 게시글_관련_요소를_통해_객체_생성이_가능하다() {
        final User author = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        final Article article = createArticle(ARTICLE_SLUG, ARTICLE_TITLE, ARTICLE_BODY, ARTICLE_DESCRIPTION, author);

        assertAll(
                () -> assertThat(article).isNotNull(),
                () -> assertThat(article).isExactlyInstanceOf(Article.class)
        );
    }

    @Test
    void 식별자가_같으면_동일한_객체이다() {
        final User author = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        final Article article = createArticle(ARTICLE_SLUG, ARTICLE_TITLE, ARTICLE_BODY, ARTICLE_DESCRIPTION, author);
        final Article other = createArticle(ARTICLE_SLUG, ARTICLE_TITLE, ARTICLE_BODY, ARTICLE_DESCRIPTION, author);

        ReflectionTestUtils.setField(article, "articleId", 1L);
        ReflectionTestUtils.setField(other, "articleId", 1L);

        assertAll(
                () -> assertThat(article).isEqualTo(other),
                () -> assertThat(article).hasSameHashCodeAs(other)
        );
    }

    @Test
    void 특정_필드들은_값을_변경할_수_있다() {
        final User author = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        final Article article = createArticle(ARTICLE_SLUG, ARTICLE_TITLE, ARTICLE_BODY, ARTICLE_DESCRIPTION, author);

        article.changeArticleTitleAndSlug(OTHER_ARTICLE_TITLE, new TestSlugStrategy())
                .changeArticleBody(OTHER_ARTICLE_BODY)
                .changeArticleDescription(OTHER_ARTICLE_DESCRIPTION);

        assertAll(
                () -> assertThat(article.articleSlug()).isEqualTo(OTHER_ARTICLE_SLUG),
                () -> assertThat(article.articleTitle()).isEqualTo(OTHER_ARTICLE_TITLE),
                () -> assertThat(article.articleBody()).isEqualTo(OTHER_ARTICLE_BODY),
                () -> assertThat(article.articleDescription()).isEqualTo(OTHER_ARTICLE_DESCRIPTION)
        );
    }
}
