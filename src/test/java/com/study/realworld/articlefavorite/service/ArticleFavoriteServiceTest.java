package com.study.realworld.articlefavorite.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import com.study.realworld.article.domain.Article;
import com.study.realworld.article.domain.ArticleContent;
import com.study.realworld.article.domain.Body;
import com.study.realworld.article.domain.Description;
import com.study.realworld.article.domain.Slug;
import com.study.realworld.article.domain.SlugTitle;
import com.study.realworld.article.domain.Title;
import com.study.realworld.article.dto.response.ArticleResponse.ArticleResponseNested;
import com.study.realworld.article.service.ArticleService;
import com.study.realworld.articlefavorite.domain.ArticleFavorite;
import com.study.realworld.articlefavorite.domain.ArticleFavoriteRepository;
import com.study.realworld.articlefavorite.dto.response.ArticleFavoriteResponse;
import com.study.realworld.global.exception.BusinessException;
import com.study.realworld.global.exception.ErrorCode;
import com.study.realworld.tag.domain.Tag;
import com.study.realworld.user.domain.ArticleFavorites;
import com.study.realworld.user.domain.Bio;
import com.study.realworld.user.domain.Email;
import com.study.realworld.user.domain.Password;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.Username;
import com.study.realworld.user.service.UserService;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ArticleFavoriteServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private ArticleService articleService;

    @Mock
    private ArticleFavoriteRepository articleFavoriteRepository;

    @InjectMocks
    private ArticleFavoriteService articleFavoriteService;

    private User user;
    private User author;
    private Article article;

    @BeforeEach
    void beforeEach() {
        user = User.Builder()
            .id(1L)
            .profile(Username.of("jake"), Bio.of("I work at statefarm"), null)
            .email(Email.of("jake@jake.jake"))
            .password(Password.of("jakejake"))
            .build();

        author = User.Builder()
            .id(2L)
            .profile(Username.of("jakefriend"), Bio.of("I work at statefarm"), null)
            .email(Email.of("jakefriend@jake.jake"))
            .password(Password.of("jakejake"))
            .build();

        ArticleContent articleContent = ArticleContent.builder()
            .slugTitle(SlugTitle.of(Title.of("How to train your dragon")))
            .description(Description.of("Ever wonder how?"))
            .body(Body.of("It takes a Jacobian"))
            .tags(Arrays.asList(Tag.of("dragons"), Tag.of("training")))
            .build();

        article = Article.from(articleContent, author);
    }

    @Nested
    @DisplayName("favoriteArticle 게시글 좋아요 테스트")
    class favoriteArticleTest {

        @Test
        @DisplayName("없는 user을 조회하려고할 때 exception이 발생해야 한다.")
        void favoriteArticleExceptionByNotFoundUserTest() {

            // given
            Long userId = 1L;
            when(userService.findById(userId)).thenThrow(new BusinessException(ErrorCode.USER_NOT_FOUND));

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> articleFavoriteService.favoriteArticle(userId, article.slug()))
                .withMessageMatching(ErrorCode.USER_NOT_FOUND.getMessage());
        }

        @Test
        @DisplayName("없는 article을 조회하려고할 때 exception이 발생해야 한다.")
        void favoriteArticleExceptionByNotFoundArticleTest() {

            // given
            Long userId = 1L;
            when(userService.findById(userId)).thenReturn(user);
            when(articleService.findBySlug(article.slug())).thenThrow(new BusinessException(ErrorCode.ARTICLE_NOT_FOUND_BY_SLUG));

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() ->  articleFavoriteService.favoriteArticle(userId, article.slug()))
                .withMessageMatching(ErrorCode.ARTICLE_NOT_FOUND_BY_SLUG.getMessage());
        }

        @Test
        @DisplayName("이미 좋아요한 게시글을 좋아요하려고할 때 exception이 발생해야 한다.")
        void favoriteArticleExceptionByAlreadyFavoriteTest() {

            // given
            Long userId = 1L;
            Slug slug = article.slug();
            Set<ArticleFavorite> favoriteSet = new HashSet<>();
            ArticleFavorite favorite = ArticleFavorite.builder()
                .user(user)
                .article(article)
                .build();
            favoriteSet.add(favorite);
            user = User.Builder()
                .id(1L)
                .profile(Username.of("jake"), Bio.of("I work at statefarm"), null)
                .email(Email.of("jake@jake.jake"))
                .password(Password.of("jakejake"))
                .articleFavorites(ArticleFavorites.of(favoriteSet))
                .build();
            when(userService.findById(userId)).thenReturn(user);
            when(articleService.findBySlug(slug)).thenReturn(article);

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() ->  articleFavoriteService.favoriteArticle(userId, slug))
                .withMessageMatching(ErrorCode.INVALID_FAVORITE_ARTICLE.getMessage());
        }

        @Test
        @DisplayName("게시글을 좋아요할 수 있다.")
        void favoriteArticleSuccessTest() {

            // given
            Long userId = 1L;
            Slug slug = article.slug();
            when(userService.findById(userId)).thenReturn(user);
            when(articleService.findBySlug(slug)).thenReturn(article);

            ArticleFavoriteResponse expected = ArticleFavoriteResponse.from(
                ArticleResponseNested.from(article, user, false, 0, false)
            );

            // when
            ArticleFavoriteResponse result = articleFavoriteService.favoriteArticle(userId, slug);

            // then
            assertThat(result).isEqualTo(expected);
        }

    }

    @Nested
    @DisplayName("unfavoriteArticle 게시글 좋아요취소 테스트")
    class unfavoriteArticleTest {

        @Test
        @DisplayName("없는 user을 조회하려고할 때 exception이 발생해야 한다.")
        void unfavoriteArticleExceptionByNotFoundUserTest() {

            // given
            Long userId = 1L;
            when(userService.findById(userId)).thenThrow(new BusinessException(ErrorCode.USER_NOT_FOUND));

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> articleFavoriteService.unfavoriteArticle(userId, article.slug()))
                .withMessageMatching(ErrorCode.USER_NOT_FOUND.getMessage());
        }

        @Test
        @DisplayName("없는 article을 조회하려고할 때 exception이 발생해야 한다.")
        void unfavoriteArticleExceptionByNotFoundArticleTest() {

            // given
            Long userId = 1L;
            when(userService.findById(userId)).thenReturn(user);
            when(articleService.findBySlug(article.slug())).thenThrow(new BusinessException(ErrorCode.ARTICLE_NOT_FOUND_BY_SLUG));

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() ->  articleFavoriteService.unfavoriteArticle(userId, article.slug()))
                .withMessageMatching(ErrorCode.ARTICLE_NOT_FOUND_BY_SLUG.getMessage());
        }

        @Test
        @DisplayName("좋아요안한 게시글을 좋아요 취소하려고할 때 exception이 발생해야 한다.")
        void unfavoriteArticleExceptionByNonFavoriteTest() {

            // given
            Long userId = 1L;
            Slug slug = article.slug();
            when(userService.findById(userId)).thenReturn(user);
            when(articleService.findBySlug(slug)).thenReturn(article);

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() ->  articleFavoriteService.unfavoriteArticle(userId, slug))
                .withMessageMatching(ErrorCode.INVALID_UNFAVORITE_ARTICLE.getMessage());
        }

        @Test
        @DisplayName("게시글을 좋아요 취소할 수 있다.")
        void favoriteArticleSuccessTest() {

            // given
            Long userId = 1L;
            Slug slug = article.slug();
            Set<ArticleFavorite> favoriteSet = new HashSet<>();
            ArticleFavorite favorite = ArticleFavorite.builder()
                .user(user)
                .article(article)
                .build();
            favoriteSet.add(favorite);
            user = User.Builder()
                .id(1L)
                .profile(Username.of("jake"), Bio.of("I work at statefarm"), null)
                .email(Email.of("jake@jake.jake"))
                .password(Password.of("jakejake"))
                .articleFavorites(ArticleFavorites.of(favoriteSet))
                .build();
            when(userService.findById(userId)).thenReturn(user);
            when(articleService.findBySlug(slug)).thenReturn(article);

            ArticleFavoriteResponse expected = ArticleFavoriteResponse.from(
                ArticleResponseNested.from(article, user, true, 0, false)
            );

            // when
            ArticleFavoriteResponse result = articleFavoriteService.unfavoriteArticle(userId, slug);

            // then
            assertThat(result).isEqualTo(expected);
        }

    }

}