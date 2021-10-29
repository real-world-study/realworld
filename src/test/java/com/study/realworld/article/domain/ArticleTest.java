package com.study.realworld.article.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.study.realworld.tag.domain.Tag;
import com.study.realworld.user.domain.Bio;
import com.study.realworld.user.domain.Email;
import com.study.realworld.user.domain.Image;
import com.study.realworld.user.domain.Password;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.Username;
import java.time.OffsetDateTime;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ArticleTest {

    private User author;
    private ArticleContent articleContent;

    @BeforeEach
    void beforeEach() {
        author = User.Builder()
            .email(Email.of("email@email.com"))
            .password(Password.of("password"))
            .profile(Username.of("username"), Bio.of("bio"), Image.of("image"))
            .build();
        articleContent = ArticleContent.builder()
            .slugTitle(SlugTitle.of(Title.of("title")))
            .description(Description.of("description"))
            .body(Body.of("body"))
            .tags(Arrays.asList(Tag.of("tag1"), Tag.of("tag2")))
            .build();
    }

    @Test
    void articleTest() {
        Article article = new Article();
    }

    @Test
    @DisplayName("title을 변경할 수 있다.")
    void changeTitleTest() {

        // given
        Title changeTitle = Title.of("title title");
        Article article = Article.from(articleContent, author);

        // when
        article.changeTitle(changeTitle);

        // then
        assertAll(
            () -> assertThat(article.title()).isEqualTo(changeTitle),
            () -> assertThat(article.slug()).isEqualTo(Slug.of(changeTitle.titleToSlug()))
        );
    }

    @Test
    @DisplayName("description을 변경할 수 있다.")
    void changeDescriptionTest() {

        // given
        Description changeDescription = Description.of("new description");
        Article article = Article.from(articleContent, author);

        // when
        article.changeDescription(changeDescription);

        // then
        assertThat(article.description()).isEqualTo(changeDescription);
    }

    @Test
    @DisplayName("body를 변경할 수 있다.")
    void changeBodyTest() {

        // given
        Body changeBody = Body.of("new body");
        Article article = Article.from(articleContent, author);

        // when
        article.changeBody(changeBody);

        // then
        assertThat(article.body()).isEqualTo(changeBody);
    }

    @Test
    @DisplayName("Article을 삭제할 때 삭제 시간을 저장할 수 있다.")
    void deleteArticleTest() {

        // given
        Article article = Article.from(articleContent, author);
        OffsetDateTime startTime = OffsetDateTime.now();
        article.deleteArticle();
        OffsetDateTime endTime = OffsetDateTime.now();

        // when
        OffsetDateTime result = article.deletedAt();

        // then
        assertThat(result).isAfter(startTime).isBefore(endTime);
    }

    @Test
    @DisplayName("유저가 글을 좋아요할 수 있다.")
    void favoritingByUserTest() {

        // given
        Article article = Article.from(articleContent, author);
        User user = User.Builder()
            .email(Email.of("email@email.com"))
            .build();
        article.favoritingByUser(user);

        // when
        boolean result = article.isFavorited();

        // then
        assertTrue(result);
    }

    @Test
    @DisplayName("유저가 글을 좋아요 취소할 수 있다.")
    void unfavoritingByUserTest() {

        // given
        Article article = Article.from(articleContent, author);
        User user = User.Builder()
            .email(Email.of("email@email.com"))
            .build();
        article.favoritingByUser(user);
        article.unfavoritingByUser(user);

        // when
        boolean result = article.isFavorited();

        // then
        assertFalse(result);
    }

    @Test
    @DisplayName("equals hashCode 테스트")
    void articleEqualsHashCodeTest() {

        // when
        Article result = Article.from(articleContent, author);

        // then
        assertThat(result)
            .isEqualTo(Article.from(articleContent, author))
            .hasSameHashCodeAs(Article.from(articleContent, author));
    }

}