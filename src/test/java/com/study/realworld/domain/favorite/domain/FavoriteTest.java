package com.study.realworld.domain.favorite.domain;

import com.study.realworld.domain.article.domain.persist.Article;
import com.study.realworld.domain.user.domain.persist.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.study.realworld.domain.article.util.ArticleFixture.*;
import static com.study.realworld.domain.user.util.UserFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("좋아요(Favorite)")
class FavoriteTest {

    @Test
    void 유저와_게시글로_객체를_생성한다() {
        final User user = createUser(OTHER_USER_EMAIL, OTHER_USER_NAME, OTHER_USER_PASSWORD, OTHER_USER_BIO, OTHER_USER_IMAGE);
        final User author = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        final Article article = createArticle(ARTICLE_SLUG, ARTICLE_TITLE, ARTICLE_BODY, ARTICLE_DESCRIPTION, author);

        final Favorite favorite = Favorite.builder().user(user).article(article).build();
        assertAll(
                () -> assertThat(favorite).isNotNull(),
                () -> assertThat(favorite).isExactlyInstanceOf(Favorite.class)
        );
    }
}
