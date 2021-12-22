package com.study.realworld.domain.favorite.domain;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.realworld.domain.article.domain.persist.Article;
import com.study.realworld.domain.user.domain.persist.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.persistence.EntityManager;

import static com.study.realworld.domain.article.util.ArticleFixture.*;
import static com.study.realworld.domain.favorite.util.FavoriteFixture.createFavorite;
import static com.study.realworld.domain.user.util.UserFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("좋아요 쿼리 레포지토리(FavoriteQueryRepository)")
@DataJpaTest
class FavoriteQueryRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private EntityManager entityManager;

    private FavoriteQueryRepository favoriteQueryRepository;

    @BeforeEach
    void setUp() {
        final JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        favoriteQueryRepository = new FavoriteQueryRepository(queryFactory);
    }

    @Test
    void 유저_정보와_게시글_정보를_통해_좋아요를_찾을수_있다() {
        final User user = createUser(OTHER_USER_EMAIL, OTHER_USER_NAME, OTHER_USER_PASSWORD, OTHER_USER_BIO, OTHER_USER_IMAGE);
        final User author = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        final Article article = createArticle(ARTICLE_SLUG, ARTICLE_TITLE, ARTICLE_BODY, ARTICLE_DESCRIPTION, author);

        testEntityManager.persist(user);
        testEntityManager.persist(author);
        testEntityManager.persist(article);
        final Favorite favorite = testEntityManager.persist(createFavorite(user, article));

        final Favorite findFavorite = favoriteQueryRepository.findByUserAndArticle(user, article).get();
        assertThat(favorite).isEqualTo(findFavorite);
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

        final long actual = favoriteQueryRepository.countByArticle(article);
        assertThat(actual).isEqualTo(1L);
    }
}
