package com.study.realworld.article.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.study.realworld.article.domain.vo.ArticleContent;
import com.study.realworld.article.domain.vo.Body;
import com.study.realworld.article.domain.vo.Description;
import com.study.realworld.article.domain.vo.Slug;
import com.study.realworld.article.domain.vo.SlugTitle;
import com.study.realworld.article.domain.vo.Title;
import com.study.realworld.tag.domain.Tag;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.vo.Bio;
import com.study.realworld.user.domain.vo.Email;
import com.study.realworld.user.domain.vo.Password;
import com.study.realworld.user.domain.vo.Username;
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
            .id(1L)
            .profile(Username.of("jake"), Bio.of("I work at statefarm"), null)
            .email(Email.of("jake@jake.jake"))
            .password(Password.of("jakejake"))
            .build();
        articleContent = ArticleContent.builder()
            .slugTitle(SlugTitle.of(Title.of("How to train your dragon")))
            .description(Description.of("Ever wonder how?"))
            .body(Body.of("It takes a Jacobian"))
            .tags(Arrays.asList(Tag.of("dragons"), Tag.of("training")))
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
    @DisplayName("현재 Article을 좋아요한 개수를 반환할 수 있다.")
    void favoritesCountTest() {

        // given
        Article article = Article.from(articleContent, author);

        // when
        int result = article.favoritesCount();

        // then
        assertThat(result).isEqualTo(0);
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