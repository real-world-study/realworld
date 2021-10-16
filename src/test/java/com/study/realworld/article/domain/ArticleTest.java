package com.study.realworld.article.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.study.realworld.tag.domain.Tag;
import com.study.realworld.user.domain.Bio;
import com.study.realworld.user.domain.Email;
import com.study.realworld.user.domain.Image;
import com.study.realworld.user.domain.Password;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.Username;
import java.util.Arrays;
import java.util.List;
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
    @DisplayName("builder test")
    void articleBuilderTest() {

        // given
        Slug slug = Slug.of("slug");
        Title title = Title.of("title");
        Description description = Description.of("description");
        Body body = Body.of("body");
        List<Tag> tags = Arrays.asList(Tag.of("tag1"), Tag.of("tag2"));

        // when
        Article result = Article.Builder()
            .slug(slug)
            .title(title)
            .description(description)
            .body(body)
            .tags(tags)
            .author(author)
            .build();

        // then
        assertAll(
            () -> assertThat(result.slug()).isEqualTo(slug),
            () -> assertThat(result.title()).isEqualTo(title),
            () -> assertThat(result.description()).isEqualTo(description),
            () -> assertThat(result.body()).isEqualTo(body),
            () -> assertThat(result.tags()).isEqualTo(tags),
            () -> assertThat(result.author()).isEqualTo(author)
        );
    }

    @Test
    @DisplayName("equals hashCode 테스트")
    void articleEqualsHashCodeTest() {

        // given
        Slug slug = Slug.of("slug");

        // when
        Article result = Article.Builder()
            .slug(slug)
            .author(author).build();

        // then
        assertThat(result)
            .isEqualTo(Article.Builder().slug(slug).author(author).build())
            .hasSameHashCodeAs(Article.Builder().slug(slug).author(author).build());
    }

}