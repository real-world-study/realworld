package com.study.realworld.article.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.study.realworld.articlefavorite.domain.ArticleFavorite;
import com.study.realworld.tag.domain.Tag;
import com.study.realworld.user.domain.Bio;
import com.study.realworld.user.domain.Email;
import com.study.realworld.user.domain.Follows;
import com.study.realworld.user.domain.Password;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.Username;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FavoritingUsersTest {

    private User user;
    private Article article;

    @BeforeEach
    void beforeEach() {
        user = User.Builder()
            .profile(Username.of("jake"), Bio.of("I work at statefarm"), null)
            .email(Email.of("jake@jake.jake"))
            .password(Password.of("jakejake"))
            .follows(Follows.of(new HashSet<>()))
            .build();
        ArticleContent articleContent = ArticleContent.builder()
            .slugTitle(SlugTitle.of(Title.of("How to train your dragon")))
            .description(Description.of("Ever wonder how?"))
            .body(Body.of("It takes a Jacobian"))
            .tags(Arrays.asList(Tag.of("dragons"), Tag.of("training")))
            .build();
        article = Article.from(articleContent, user);
    }

    @Test
    void favoritingUsers() {
        FavoritingUsers favoritingUsers = new FavoritingUsers();
    }

    @Test
    @DisplayName("현재 좋아요 개수를 반환할 수 있다.")
    void sizeTest() {

        // given
        ArticleFavorite articleFavorite = ArticleFavorite.builder()
            .user(user)
            .article(article)
            .build();
        Set<ArticleFavorite> articleFavoriteSet = new HashSet<>();
        articleFavoriteSet.add(articleFavorite);
        FavoritingUsers favoritingUsers = FavoritingUsers.of(articleFavoriteSet);

        // when
        int result = favoritingUsers.size();

        // then
        assertThat(result).isEqualTo(articleFavoriteSet.size());
    }

    @Test
    @DisplayName("equals hashCode 테스트")
    void favoritingUsersEqualsHashCodeTest() {

        // given
        ArticleFavorite articleFavorite = ArticleFavorite.builder()
            .user(user)
            .article(article)
            .build();
        Set<ArticleFavorite> articleFavoriteSet = new HashSet<>();
        articleFavoriteSet.add(articleFavorite);

        // when
        FavoritingUsers result = FavoritingUsers.of(articleFavoriteSet);

        // then
        assertThat(result)
            .isEqualTo(FavoritingUsers.of(articleFavoriteSet))
            .hasSameHashCodeAs(FavoritingUsers.of(articleFavoriteSet));
    }

}