package com.study.realworld.article.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.study.realworld.global.config.JpaConfiguration;
import com.study.realworld.tag.domain.Tag;
import com.study.realworld.user.domain.Email;
import com.study.realworld.user.domain.Password;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.UserRepository;
import com.study.realworld.user.domain.Username;
import java.util.Arrays;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DataJpaTest(includeFilters = @ComponentScan.Filter(
    type = FilterType.ASSIGNABLE_TYPE,
    classes = JpaConfiguration.class
))
class ArticleRepositoryTest {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    void beforeEach() {
        String username = "user";
        String email = "@test.com";
        for (int i = 1; i <= 10; i++) {
            userRepository.save(User.Builder()
                .profile(Username.of(username + i), null, null)
                .email(Email.of(username + i + email))
                .password(Password.of("password"))
                .build());
        }

        String title = "title";
        String description = "description";
        String body = "body";
        User author1 = userRepository.findById(1L).orElse(null);
        for (int i = 1; i <= 10; i++) {
            ArticleContent articleContent = ArticleContent.builder()
                .slugTitle(SlugTitle.of(Title.of(title + i)))
                .description(Description.of(description + i))
                .body(Body.of(body + i))
                .tags(Arrays.asList(Tag.of("tag" + i), Tag.of("tagA")))
                .build();
            Article article = articleRepository.save(Article.from(articleContent, author1));
            System.out.println("input article tag " + i + " : " + article.tags().get(0).name());
        }

        User author2 = userRepository.findById(2L).orElse(null);
        for (int i = 11; i <= 20; i++) {
            ArticleContent articleContent = ArticleContent.builder()
                .slugTitle(SlugTitle.of(Title.of(title + i)))
                .description(Description.of(description + i))
                .body(Body.of(body + i))
                .tags(Arrays.asList(Tag.of("tag" + i), Tag.of("tagB")))
                .build();
            articleRepository.save(Article.from(articleContent, author2));
        }

        User author3 = userRepository.findById(3L).orElse(null);
        for (int i = 21; i <= 30; i++) {
            ArticleContent articleContent = ArticleContent.builder()
                .slugTitle(SlugTitle.of(Title.of(title + i)))
                .description(Description.of(description + i))
                .body(Body.of(body + i))
                .tags(Arrays.asList(Tag.of("tag" + i), Tag.of("tagC")))
                .build();
            articleRepository.save(Article.from(articleContent, author3));
        }
        entityManager.flush();
        entityManager.clear();
    }

    @Test
    @DisplayName("findAllTest")
    void findAllByNameSuccessTest() {

        // given
        int offset = 0;
        int limit = 4;
        PageRequest pageRequest = PageRequest.of(offset, limit);

        // when
        Page<Article> result = articleRepository.findPageByAuthorAndTag(pageRequest, "user1", "tagA");

        // then
        assertAll(
            () -> assertThat(result.getSize()).isEqualTo(limit),    // now page content's count
            () -> assertThat(result.getNumber()).isEqualTo(offset),  // now page number
            () -> assertThat(result.getTotalPages()).isEqualTo(3),  // totla page count
            () -> {
                for (Article article : result) {
                    assertAll(
                        () -> assertThat(article.author().username()).isEqualTo(Username.of("user1")),
                        () -> assertThat(article.tags()).contains(Tag.of("tagA"))
                    );
                }
            }
        );
    }

}