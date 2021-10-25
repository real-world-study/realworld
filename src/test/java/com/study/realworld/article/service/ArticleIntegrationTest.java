package com.study.realworld.article.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.study.realworld.article.domain.Article;
import com.study.realworld.article.domain.ArticleContent;
import com.study.realworld.article.domain.ArticleRepository;
import com.study.realworld.article.domain.Body;
import com.study.realworld.article.domain.Description;
import com.study.realworld.article.domain.SlugTitle;
import com.study.realworld.article.domain.Title;
import com.study.realworld.tag.domain.Tag;
import com.study.realworld.tag.domain.TagRepository;
import com.study.realworld.user.domain.Email;
import com.study.realworld.user.domain.Password;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.Username;
import com.study.realworld.user.service.UserService;
import java.util.Arrays;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class ArticleIntegrationTest {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private EntityManager entityManager;

    private User user;
    private ArticleContent articleContent;

    @BeforeEach
    void beforeEachTest() {
        user = User.Builder()
            .profile(Username.of("username"), null, null)
            .email(Email.of("email@email.com"))
            .password(Password.of("password"))
            .build();
        articleContent = ArticleContent.builder()
            .slugTitle(SlugTitle.of(Title.of("title")))
            .description(Description.of("description"))
            .body(Body.of("body"))
            .tags(Arrays.asList(Tag.of("tag")))
            .build();
    }

    @Test
    @DisplayName("이미 저장되어있는 tag가 새로 들어오면 영속성 관리가 되어 tag를 또 저장하지 않고 해당 tag로 저장해야한다.")
    void createArticleByExistTagTest() {

        // given
        Tag existTag = Tag.of("tag");
        Tag expected = tagRepository.save(existTag);
        User author = userService.join(user);
        entityManager.clear();

        // when
        Article result = articleService.createArticle(author.id(), articleContent);

        // then
        assertAll(
            () -> assertThat(result.tags().get(0)).isEqualTo(expected),
            () -> assertThat(tagRepository.findAll().size()).isEqualTo(1)
        );
    }

    @Test
    @DisplayName("author와 slug로 찾은 Article을 soft delete 할 수 있다.")
    void deleteArticleBySlugSuccessTest() {

        // given
        userService.join(user);
        Article article = Article.from(articleContent, user);
        articleRepository.save(article);
        entityManager.clear();

        articleService.deleteArticleByAuthorAndSlug(user.id(), article.slug());

        // when
        Optional<Article> result = articleRepository.findByAuthorAndArticleContentSlugTitleSlug(user, article.slug());

        // then
        assertThat(result).isEmpty();
    }

}
