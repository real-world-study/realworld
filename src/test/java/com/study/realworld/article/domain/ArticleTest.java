package com.study.realworld.article.domain;

import static org.assertj.core.api.Assertions.assertThat;

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

    @BeforeEach
    void beforeEach() {
        author = User.Builder()
            .email(Email.of("email@email.com"))
            .password(Password.of("password"))
            .profile(Username.of("username"), Bio.of("bio"), Image.of("image"))
            .build();
    }

    @Test
    void articleTest() {
        Article article = new Article();
    }

    @Test
    @DisplayName("Article을 삭제할 때 삭제 시간을 저장할 수 있다.")
    void deleteArticleTest() {

        // given
        ArticleContent articleContent = ArticleContent.builder()
            .slugTitle(SlugTitle.of(Title.of("title")))
            .description(Description.of("description"))
            .body(Body.of("body"))
            .build();
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
    @DisplayName("equals hashCode 테스트")
    void articleEqualsHashCodeTest() {

        // given
        ArticleContent articleContent = ArticleContent.builder()
            .slugTitle(SlugTitle.of(Title.of("title")))
            .description(Description.of("description"))
            .body(Body.of("body"))
            .tags(Arrays.asList(Tag.of("tag")))
            .build();

        // when
        Article result = Article.from(articleContent, author);

        // then
        assertThat(result)
            .isEqualTo(Article.from(articleContent, author))
            .hasSameHashCodeAs(Article.from(articleContent, author));
    }

}