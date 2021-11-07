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
import com.study.realworld.article.dto.response.ArticleResponse;
import com.study.realworld.article.dto.response.ArticleResponses;
import com.study.realworld.articlefavorite.domain.ArticleFavorite;
import com.study.realworld.articlefavorite.domain.ArticleFavoriteRepository;
import com.study.realworld.tag.domain.Tag;
import com.study.realworld.tag.domain.TagRepository;
import com.study.realworld.user.domain.Bio;
import com.study.realworld.user.domain.Email;
import com.study.realworld.user.domain.Password;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.Username;
import com.study.realworld.user.service.UserService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class ArticleServiceIntegrationTest {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private UserService userService;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleFavoriteRepository articleFavoriteRepository;

    @Autowired
    private EntityManager entityManager;

    private User user;
    private User favoritingUser;
    private ArticleContent articleContent;

    @BeforeEach
    void beforeEachTest() {
        user = User.Builder()
            .profile(Username.of("jake"), Bio.of("I work at statefarm"), null)
            .email(Email.of("jake@jake.jake"))
            .password(Password.of("jakejake"))
            .build();

        favoritingUser = User.Builder()
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
    }

    @Test
    @DisplayName("이미 저장되어있는 tag가 새로 들어오면 영속성 관리가 되어 tag를 또 저장하지 않고 해당 tag로 저장해야한다.")
    void createArticleByExistTagTest() {

        // given
        Tag existTag = Tag.of("tag");
        articleContent.tags()
            .forEach(tag -> tagRepository.save(Tag.of(tag)));
        tagRepository.save(existTag);
        User author = userService.join(user);

        Article article = Article.from(articleContent, author);
        ArticleResponse expected = ArticleResponse.fromArticle(article);
        entityManager.flush();
        entityManager.clear();

        // when
        ArticleResponse result = articleService.createArticle(author.id(), articleContent);
        entityManager.clear();

        // then
        assertAll(
            () -> assertThat(result).isEqualTo(expected),
            () -> assertThat(tagRepository.findAll().size()).isEqualTo(3)
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

    @Test
    @DisplayName("원하는 tag와 author를 가진 articles page를 반환받을 수 있다.")
    void findAllArticlesTest() {

        // setup
        userService.join(user);
        userService.join(favoritingUser);

        List<Article> articles = new ArrayList<>();
        for (int i=1; i<=10; i++){
            ArticleContent articleContent = ArticleContent.builder()
                .slugTitle(SlugTitle.of(Title.of("How to train your dragon" + i)))
                .description(Description.of("Ever wonder how?" + i))
                .body(Body.of("It takes a Jacobian" + i))
                .tags(Arrays.asList(Tag.of("dragons"), Tag.of("training")))
                .build();
            articles.add(Article.from(articleContent, user));
        }

        for (Article inputArticle : articles) {
            Article article = articleRepository.save(inputArticle);
            articleFavoriteRepository.save(ArticleFavorite.builder().user(favoritingUser).article(article).build());
        }
        entityManager.flush();
        entityManager.clear();

        // given
        int offset = 0; // page number
        int limit = 4;  // airtlce counts
        PageRequest pageRequest = PageRequest.of(offset, limit);

        ArticleResponses expected = ArticleResponses.fromArticles(articleRepository.findAll().subList(0, 4));

        // when
        ArticleResponses result = articleService.findArticleResponsesByTagAndAuthorAndFavorited(pageRequest, "dragons", "jake", "jakefriend");

        // then
        assertThat(result).isEqualTo(expected);
    }

}
