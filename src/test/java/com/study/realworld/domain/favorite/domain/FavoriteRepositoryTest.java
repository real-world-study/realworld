package com.study.realworld.domain.favorite.domain;

import com.study.realworld.domain.article.domain.persist.Article;
import com.study.realworld.domain.user.domain.persist.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static com.study.realworld.domain.article.util.ArticleFixture.*;
import static com.study.realworld.domain.user.util.UserFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class FavoriteRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Test
    void 좋아요_정보가_존재한다면_유저와_게시글을_통해_좋아요를_찾을수_있다() {
        final User user = createUser(OTHER_USER_EMAIL, OTHER_USER_NAME, OTHER_USER_PASSWORD, OTHER_USER_BIO, OTHER_USER_IMAGE);
        final User author = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        final Article article = createArticle(ARTICLE_SLUG, ARTICLE_TITLE, ARTICLE_BODY, ARTICLE_DESCRIPTION, author);

        testEntityManager.persist(user);
        testEntityManager.persist(author);
        testEntityManager.persist(article);
        testEntityManager.persist(createFavorite(user, article));

        final boolean actual = favoriteRepository.findByUserAndArticle(user, article).isPresent();
        assertThat(actual).isTrue();
    }

    @Test
    void 좋아요_정보가_존재하지_않는다면_유저와_게시글을_통해_좋아요를_찾을수_없다() {
        final User user = createUser(OTHER_USER_EMAIL, OTHER_USER_NAME, OTHER_USER_PASSWORD, OTHER_USER_BIO, OTHER_USER_IMAGE);
        final User author = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        final Article article = createArticle(ARTICLE_SLUG, ARTICLE_TITLE, ARTICLE_BODY, ARTICLE_DESCRIPTION, author);

        testEntityManager.persist(user);
        testEntityManager.persist(author);
        testEntityManager.persist(article);

        final boolean actual = favoriteRepository.findByUserAndArticle(user, article).isPresent();
        assertThat(actual).isFalse();
    }

    @Test
    void 게시글의_좋아요_수를_반환한다() {
        final User user = createUser(OTHER_USER_EMAIL, OTHER_USER_NAME, OTHER_USER_PASSWORD, OTHER_USER_BIO, OTHER_USER_IMAGE);
        final User author = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        final Article article = createArticle(ARTICLE_SLUG, ARTICLE_TITLE, ARTICLE_BODY, ARTICLE_DESCRIPTION, author);

        testEntityManager.persist(user);
        testEntityManager.persist(author);
        testEntityManager.persist(article);
        testEntityManager.persist(createFavorite(user, article));

        final long actual = favoriteRepository.countByArticle(article);
        assertThat(actual).isEqualTo(1);
    }

    private Favorite createFavorite(final User user, final Article article) {
        return Favorite.builder()
                .article(article)
                .user(user)
                .build();
    }
}
