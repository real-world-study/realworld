package com.study.realworld.article.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.study.realworld.article.domain.vo.ArticleContent;
import com.study.realworld.article.domain.vo.Body;
import com.study.realworld.article.domain.vo.Description;
import com.study.realworld.article.domain.vo.SlugTitle;
import com.study.realworld.article.domain.vo.Title;
import com.study.realworld.articlefavorite.domain.ArticleFavorite;
import com.study.realworld.articlefavorite.domain.ArticleFavoriteRepository;
import com.study.realworld.global.config.JpaConfiguration;
import com.study.realworld.tag.domain.Tag;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.UserRepository;
import com.study.realworld.user.domain.vo.Bio;
import com.study.realworld.user.domain.vo.Email;
import com.study.realworld.user.domain.vo.Password;
import com.study.realworld.user.domain.vo.Username;
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
    private ArticleFavoriteRepository articleFavoriteRepository;

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

        User favoritingUser = userRepository.save(User.Builder()
            .profile(Username.of("jakefriend"), Bio.of("I work at statefarm"), null)
            .email(Email.of("jakefriend@jake.jake"))
            .password(Password.of("jakejake"))
            .build());

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
            articleFavoriteRepository.save(ArticleFavorite.builder().user(favoritingUser).article(article).build());
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
    @DisplayName("특정 tag와 username와 favorited user를 가진 작성자의 article을 검색할 수 있다.")
    void findAllByAuthornameTagSuccessTest() {

        // given
        int offset = 0;
        int limit = 4;
        PageRequest pageRequest = PageRequest.of(offset, limit);

        // when
        Page<Article> result = articleRepository.findPageByTagAndAuthorAndFavorited(pageRequest, "tagA", "user1", "jakefriend");

        // then
        assertAll(
            () -> assertThat(result.getSize()).isEqualTo(limit),    // now page content's count
            () -> assertThat(result.getNumber()).isEqualTo(offset),  // now page number
            () -> assertThat(result.getTotalPages()).isEqualTo(3),  // totla page count
            () -> {
                for (Article article : result) {
                    assertAll(
                        () -> assertThat(article.author().username()).isEqualTo(Username.of("user1")),
                        () -> assertThat(article.tags()).contains("tagA")
                    );
                }
            }
        );
    }

}