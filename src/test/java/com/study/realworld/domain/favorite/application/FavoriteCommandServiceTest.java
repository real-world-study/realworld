package com.study.realworld.domain.favorite.application;

import com.study.realworld.domain.article.application.ArticleQueryService;
import com.study.realworld.domain.article.domain.persist.Article;
import com.study.realworld.domain.favorite.domain.Favorite;
import com.study.realworld.domain.favorite.domain.FavoriteRepository;
import com.study.realworld.domain.favorite.dto.FavoriteInfo;
import com.study.realworld.domain.favorite.dto.UnFavoriteInfo;
import com.study.realworld.domain.follow.application.FollowQueryService;
import com.study.realworld.domain.user.application.UserQueryService;
import com.study.realworld.domain.user.domain.persist.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

import static com.study.realworld.domain.article.util.ArticleFixture.*;
import static com.study.realworld.domain.user.util.UserFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willReturn;

@DisplayName("FavoriteCommandService")
@ExtendWith(MockitoExtension.class)
class FavoriteCommandServiceTest {

    @Mock
    private FavoriteRepository favoriteRepository;

    @Mock
    private UserQueryService userQueryService;

    @Mock
    private ArticleQueryService articleQueryService;

    @Mock
    private FollowQueryService followQueryService;

    @InjectMocks
    private FavoriteCommandService favoriteCommandService;

    @Test
    void 게시글에_좋아요를_누른다() {
        final User user = createUser(OTHER_USER_EMAIL, OTHER_USER_NAME, OTHER_USER_PASSWORD, OTHER_USER_BIO, OTHER_USER_IMAGE);
        final User author = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        final Article article = createArticle(ARTICLE_SLUG, ARTICLE_TITLE, ARTICLE_BODY, ARTICLE_DESCRIPTION, author);

        ReflectionTestUtils.setField(article, "createdAt", LocalDateTime.now());
        ReflectionTestUtils.setField(article, "updatedAt", LocalDateTime.now());

        willReturn(user).given(userQueryService).findById(any());
        willReturn(article).given(articleQueryService).findByArticleSlug(any());
        willReturn(true).given(followQueryService).existsByFolloweeAndFollower(article.author(), user);
        willReturn(Optional.empty()).given(favoriteRepository).findByUserAndArticle(any(), any());
        willReturn(createFavorite(user, article)).given(favoriteRepository).save(any());
        willReturn(1L).given(favoriteRepository).countByArticle(any());

        final FavoriteInfo favoriteInfo = favoriteCommandService.favorite(1L, ARTICLE_SLUG);
        assertAll(
                () -> assertThat(favoriteInfo.articleSlug()).isEqualTo(ARTICLE_SLUG),
                () -> assertThat(favoriteInfo.articleTitle()).isEqualTo(ARTICLE_TITLE),
                () -> assertThat(favoriteInfo.articleBody()).isEqualTo(ARTICLE_BODY),
                () -> assertThat(favoriteInfo.articleDescription()).isEqualTo(ARTICLE_DESCRIPTION),
                () -> assertThat(favoriteInfo.favorited()).isTrue(),
                () -> assertThat(favoriteInfo.favoritesCount()).isEqualTo(1),
                () -> assertThat(favoriteInfo.tagNames()).isEqualTo(Set.of()),
                () -> assertThat(favoriteInfo.authorInfo().userName()).isEqualTo(USER_NAME),
                () -> assertThat(favoriteInfo.authorInfo().userBio()).isEqualTo(USER_BIO),
                () -> assertThat(favoriteInfo.authorInfo().userImage()).isEqualTo(USER_IMAGE),
                () -> assertThat(favoriteInfo.authorInfo().following()).isTrue()
        );
    }

    @Test
    void 게시글에_좋아요를_취소한다() {
        final User user = createUser(OTHER_USER_EMAIL, OTHER_USER_NAME, OTHER_USER_PASSWORD, OTHER_USER_BIO, OTHER_USER_IMAGE);
        final User author = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        final Article article = createArticle(ARTICLE_SLUG, ARTICLE_TITLE, ARTICLE_BODY, ARTICLE_DESCRIPTION, author);

        ReflectionTestUtils.setField(article, "createdAt", LocalDateTime.now());
        ReflectionTestUtils.setField(article, "updatedAt", LocalDateTime.now());

        willReturn(user).given(userQueryService).findById(any());
        willReturn(article).given(articleQueryService).findByArticleSlug(any());
        willReturn(true).given(followQueryService).existsByFolloweeAndFollower(article.author(), user);
        willReturn(Optional.ofNullable(createFavorite(user, article))).given(favoriteRepository).findByUserAndArticle(any(), any());
        willDoNothing().given(favoriteRepository).delete(any());
        willReturn(0L).given(favoriteRepository).countByArticle(any());

        final UnFavoriteInfo unFavoriteInfo = favoriteCommandService.unFavorite(1L, ARTICLE_SLUG);
        assertAll(
                () -> assertThat(unFavoriteInfo.articleSlug()).isEqualTo(ARTICLE_SLUG),
                () -> assertThat(unFavoriteInfo.articleTitle()).isEqualTo(ARTICLE_TITLE),
                () -> assertThat(unFavoriteInfo.articleBody()).isEqualTo(ARTICLE_BODY),
                () -> assertThat(unFavoriteInfo.articleDescription()).isEqualTo(ARTICLE_DESCRIPTION),
                () -> assertThat(unFavoriteInfo.favorited()).isFalse(),
                () -> assertThat(unFavoriteInfo.favoritesCount()).isEqualTo(0),
                () -> assertThat(unFavoriteInfo.tagNames()).isEqualTo(Set.of()),
                () -> assertThat(unFavoriteInfo.authorInfo().userName()).isEqualTo(USER_NAME),
                () -> assertThat(unFavoriteInfo.authorInfo().userBio()).isEqualTo(USER_BIO),
                () -> assertThat(unFavoriteInfo.authorInfo().userImage()).isEqualTo(USER_IMAGE),
                () -> assertThat(unFavoriteInfo.authorInfo().following()).isTrue()
        );
    }

    private Favorite createFavorite(final User user, final Article article) {
        return Favorite.builder()
                .user(user)
                .article(article)
                .build();
    }
}
