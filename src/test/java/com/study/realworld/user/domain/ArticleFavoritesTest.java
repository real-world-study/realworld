package com.study.realworld.user.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.study.realworld.article.domain.Article;
import com.study.realworld.article.domain.ArticleContent;
import com.study.realworld.article.domain.Body;
import com.study.realworld.article.domain.Description;
import com.study.realworld.article.domain.SlugTitle;
import com.study.realworld.article.domain.Title;
import com.study.realworld.articlefavorite.domain.ArticleFavorite;
import com.study.realworld.global.exception.BusinessException;
import com.study.realworld.global.exception.ErrorCode;
import com.study.realworld.tag.domain.Tag;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ArticleFavoritesTest {

    private User user;
    private User author;
    private ArticleContent articleContent;
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

        articleContent = ArticleContent.builder()
            .slugTitle(SlugTitle.of(Title.of("How to train your dragon")))
            .description(Description.of("Ever wonder how?"))
            .body(Body.of("It takes a Jacobian"))
            .tags(Arrays.asList(Tag.of("dragons"), Tag.of("training")))
            .build();

        article = Article.from(articleContent, author);
    }

    @Test
    void articleFavoritesTest() {
        ArticleFavorites articleFavorite = new ArticleFavorites();
    }

    @Nested
    @DisplayName("favoritingArticle 게시글 좋아요 기능 테스트")
    class favoritingArticleTest {

        @Test
        @DisplayName("이미 좋아요한 유저가 좋아요하는 경우 exception이 발생해야 한다.")
        void favoritingArticleExceptionByExistFavoriteTest() {

            // given
            Set<ArticleFavorite> favoriteSet = new HashSet<>();
            ArticleFavorite favorite = ArticleFavorite.builder()
                .user(user)
                .article(article)
                .build();
            favoriteSet.add(favorite);
            ArticleFavorites favorites = ArticleFavorites.of(favoriteSet);

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> favorites.favoriting(favorite))
                .withMessageMatching(ErrorCode.INVALID_FAVORITE_ARTICLE.getMessage());
        }

        @Test
        @DisplayName("정상적으로 게시글을 좋아요할 수 있다.")
        void favoritingArticleSuccessTest() {

            // given
            Set<ArticleFavorite> favoriteSet = new HashSet<>();
            ArticleFavorite favorite = ArticleFavorite.builder()
                .user(user)
                .article(article)
                .build();
            ArticleFavorites favorites = ArticleFavorites.of(favoriteSet);

            Set<ArticleFavorite> expectedSet = new HashSet<>();
            expectedSet.add(favorite);
            ArticleFavorites expected = ArticleFavorites.of(expectedSet);

            // when
            favorites.favoriting(favorite);

            // then
            assertThat(favorites).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("unfavoritingArticle 게시글 좋아요취소 기능 테스트")
    class unfavoritingArticleTest {

        @Test
        @DisplayName("좋아요 안한 유저가 좋아요 취소하는 경우 exception이 발생해야 한다.")
        void unfavoritingArticleExceptionByNoExistFavoriteTest() {

            // given
            Set<ArticleFavorite> favoriteSet = new HashSet<>();
            ArticleFavorite favorite = ArticleFavorite.builder()
                .user(user)
                .article(article)
                .build();
            ArticleFavorites favorites = ArticleFavorites.of(favoriteSet);

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> favorites.unfavoriting(favorite))
                .withMessageMatching(ErrorCode.INVALID_UNFAVORITE_ARTICLE.getMessage());
        }

        @Test
        @DisplayName("정상적으로 게시글을 좋아요취소 할 수 있다.")
        void unfavoritingArticleSuccessTest() {

            // given
            Set<ArticleFavorite> favoriteSet = new HashSet<>();
            ArticleFavorite favorite = ArticleFavorite.builder()
                .user(user)
                .article(article)
                .build();
            favoriteSet.add(favorite);
            ArticleFavorites favorites = ArticleFavorites.of(favoriteSet);

            ArticleFavorites expected = ArticleFavorites.of(new HashSet<>());

            // when
            favorites.unfavoriting(favorite);

            // then
            assertThat(favorites).isEqualTo(expected);
        }
    }

    @Nested
    @DisplayName("isFavoriteArticle 팔로윙 유무체크 기능 테스트")
    class isFavoriteArticleTest {

        private ArticleFavorite articleFavorite = ArticleFavorite.builder()
            .user(user)
            .article(article)
            .build();

        @Test
        @DisplayName("true")
        void trueTest() {

            // given
            Set<ArticleFavorite> articleFavoriteSet = new HashSet<>();
            articleFavoriteSet.add(articleFavorite);
            ArticleFavorites favorites = ArticleFavorites.of(articleFavoriteSet);

            // when
            boolean result = favorites.isFavoriteArticle(articleFavorite);

            // then
            assertTrue(result);
        }

        @Test
        @DisplayName("false")
        void falseTest() {

            // given
            ArticleFavorites favorites = ArticleFavorites.of(new HashSet<>());

            // when
            boolean result = favorites.isFavoriteArticle(articleFavorite);

            // then
            assertFalse(result);
        }

    }

    @Test
    @DisplayName("equals hashCode 테스트")
    void favoriteArticlesEqualsHashCodeTest() {

        // given
        Set<ArticleFavorite> articleFavoriteSet = new HashSet<>();
        ArticleFavorite favorite = ArticleFavorite.builder()
            .user(user)
            .article(article)
            .build();
        articleFavoriteSet.add(favorite);

        // when
        ArticleFavorites result = ArticleFavorites.of(articleFavoriteSet);

        // then
        assertThat(result)
            .isEqualTo(ArticleFavorites.of(articleFavoriteSet))
            .hasSameHashCodeAs(ArticleFavorites.of(articleFavoriteSet));
    }

}