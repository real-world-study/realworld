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
import com.study.realworld.tag.service.TagService;
import com.study.realworld.user.domain.Email;
import com.study.realworld.user.domain.Password;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.Username;
import com.study.realworld.user.service.UserService;
import java.util.Arrays;
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
    private TagService tagService;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private EntityManager entityManager;

    private User user;

    @BeforeEach
    void beforeEachTest() {
        user = User.Builder()
            .profile(Username.of("username"), null, null)
            .email(Email.of("email@email.com"))
            .password(Password.of("password"))
            .build();
    }

    @Test
    @DisplayName("이미 저장되어있는 tag가 새로 들어오면 영속성 관리가 되어 tag를 또 저장하지 않고 해당 tag로 저장해야한다.")
    void createArticleByExistTagTest() {

        // given
        Tag existTag = Tag.of("tag");
        Tag expected = tagRepository.save(existTag);
        User author = userService.join(user);
        ArticleContent articleContent = ArticleContent.builder()
            .slugTitle(SlugTitle.of(Title.of("title")))
            .description(Description.of("description"))
            .body(Body.of("body"))
            .tags(Arrays.asList(Tag.of("tag")))
            .build();
        entityManager.clear();

        // when
        Article result = articleService.createArticle(author.id(), articleContent);

        // then
        assertAll(
            () -> assertThat(result.tags().get(0)).isEqualTo(expected),
            () -> assertThat(tagRepository.findAll().size()).isEqualTo(1)
        );
    }
}
