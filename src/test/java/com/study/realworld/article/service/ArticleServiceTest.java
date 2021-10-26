package com.study.realworld.article.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

import com.study.realworld.article.domain.Article;
import com.study.realworld.article.domain.ArticleContent;
import com.study.realworld.article.domain.ArticleRepository;
import com.study.realworld.article.domain.Body;
import com.study.realworld.article.domain.Description;
import com.study.realworld.article.domain.Slug;
import com.study.realworld.article.domain.SlugTitle;
import com.study.realworld.article.domain.Title;
import com.study.realworld.article.service.model.ArticleUpdateModel;
import com.study.realworld.global.exception.BusinessException;
import com.study.realworld.global.exception.ErrorCode;
import com.study.realworld.tag.domain.Tag;
import com.study.realworld.tag.service.TagService;
import com.study.realworld.user.domain.Email;
import com.study.realworld.user.domain.Password;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.Username;
import com.study.realworld.user.service.UserService;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private UserService userService;

    @Mock
    private TagService tagService;

    @InjectMocks
    private ArticleService articleService;

    private User user;

    private ArticleContent articleContent;

    @BeforeEach
    void beforeEach() {
        user = User.Builder()
            .id(1L)
            .profile(Username.of("username"), null, null)
            .password(Password.of("password"))
            .email(Email.of("email@email.com"))
            .build();
        articleContent = ArticleContent.builder()
            .slugTitle(SlugTitle.of(Title.of("title")))
            .description(Description.of("description"))
            .body(Body.of("body"))
            .tags(Arrays.asList(Tag.of("tag1"), Tag.of("tag2")))
            .build();
    }

    @Nested
    @DisplayName("findBySlug Article 단일 조회 테스트")
    class findBySlugTest {

        @Test
        @DisplayName("slug를 가지고 article을 조회할 수 있다.")
        void findBySlugSuccessTest() {

            // given
            Article expected = Article.from(articleContent, user);
            when(articleRepository.findByArticleContentSlugTitleSlug(expected.slug()))
                .thenReturn(Optional.of(expected));

            // when
            Article result = articleService.findBySlug(expected.slug());

            // then
            assertThat(result).isEqualTo(expected);
        }

        @Test
        @DisplayName("없는 article을 조회하려고할 때 exception이 발생해야 한다.")
        void findBySlugExceptionTest() {

            // given
            Article article = Article.from(articleContent, user);
            when(articleRepository.findByArticleContentSlugTitleSlug(article.slug()))
                .thenReturn(Optional.empty());

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> articleService.findBySlug(article.slug()))
                .withMessageMatching(ErrorCode.ARTICLE_NOT_FOUND_BY_SLUG.getMessage());
        }

    }

    @Test
    @DisplayName("user id를 가지고 article을 생성할 수 있다.")
    void createArticleTest() {

        // setup & given
        Long userId = 1L;
        when(userService.findById(userId)).thenReturn(user);
        when(tagService.refreshTagByExistedTag(articleContent.tags()))
            .thenReturn(Arrays.asList(Tag.of("tag1"), Tag.of("tag2")));
        Article expected = Article.from(articleContent, user);
        when(articleRepository.save(expected)).thenReturn(expected);

        // when
        Article result = articleService.createArticle(userId, articleContent);

        // then
        assertThat(result).isEqualTo(expected);
    }

    @Nested
    @DisplayName("updateARticle Article 변경 테스트")
    class updateArticle {

        private ArticleUpdateModel articleUpdateModel;

        @Test
        @DisplayName("유저가 존재하지 않으면 exception이 발생해야 한다.")
        void userNotFoundExceptionTest() {

            // given
            Article article = Article.from(articleContent, user);
            Long userId = 2L;
            Slug slug = article.slug();
            when(userService.findById(userId)).thenThrow(new BusinessException(ErrorCode.USER_NOT_FOUND));

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> articleService.updateArticle(userId, slug, articleUpdateModel))
                .withMessageMatching(ErrorCode.USER_NOT_FOUND.getMessage());
        }

        @Test
        @DisplayName("Article이 존재하지 않으면 exception이 발생해야 한다.")
        void updateArticleBySlugExceptionTest() {

            // given
            Article article = Article.from(articleContent, user);
            Long userId = 1L;
            Slug slug = article.slug();
            when(userService.findById(userId)).thenReturn(user);
            when(articleRepository.findByAuthorAndArticleContentSlugTitleSlug(user, slug)).thenReturn(Optional.empty());

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> articleService.updateArticle(userId, slug, articleUpdateModel))
                .withMessageMatching(ErrorCode.ARTICLE_NOT_FOUND_BY_AUTHOR_AND_SLUG.getMessage());
        }

        @Test
        @DisplayName("title이 변경될 수 있다.")
        void changeTitleTest() {

            // given
            Article article = Article.from(articleContent, user);
            Long userId = 1L;
            when(userService.findById(userId)).thenReturn(user);
            when(articleRepository.findByAuthorAndArticleContentSlugTitleSlug(user, article.slug()))
                .thenReturn(Optional.of(article));
            articleUpdateModel = new ArticleUpdateModel(Title.of("title title"), null, null);

            // when
            articleService.updateArticle(userId, article.slug(), articleUpdateModel);

            // then
            assertAll(
                () -> assertThat(article.title()).isEqualTo(Title.of("title title")),
                () -> assertThat(article.slug()).isEqualTo(Slug.of("title-title"))
            );
        }

        @Test
        @DisplayName("description이 변경될 수 있다.")
        void changeDescriptionTest() {

            // given
            Article article = Article.from(articleContent, user);
            Long userId = 1L;
            when(userService.findById(userId)).thenReturn(user);
            when(articleRepository.findByAuthorAndArticleContentSlugTitleSlug(user, article.slug()))
                .thenReturn(Optional.of(article));
            Description changeDescription = Description.of("new descriptioin");
            articleUpdateModel = new ArticleUpdateModel(null, changeDescription, null);

            // when
            articleService.updateArticle(userId, article.slug(), articleUpdateModel);

            // then
            assertThat(article.description()).isEqualTo(changeDescription);
        }

        @Test
        @DisplayName("body가 변경될 수 있다.")
        void changeBodyTest() {

            // given
            Article article = Article.from(articleContent, user);
            Long userId = 1L;
            when(userService.findById(userId)).thenReturn(user);
            when(articleRepository.findByAuthorAndArticleContentSlugTitleSlug(user, article.slug()))
                .thenReturn(Optional.of(article));
            Body changeBody = Body.of("new body");
            articleUpdateModel = new ArticleUpdateModel(null, null, changeBody);

            // when
            articleService.updateArticle(userId, article.slug(), articleUpdateModel);

            // then
            assertThat(article.body()).isEqualTo(changeBody);
        }

    }

    @Nested
    @DisplayName("deleteArticleByAuthorAndSlug Article 삭제 테스트")
    class deleteArticleByAuthorAndSlug {

        @Test
        @DisplayName("유저가 존재하지 않으면 exception이 발생해야 한다.")
        void userNotFoundExceptionTest() {

            // given
            Article article = Article.from(articleContent, user);
            Long userId = 2L;
            Slug slug = article.slug();
            when(userService.findById(userId)).thenThrow(new BusinessException(ErrorCode.USER_NOT_FOUND));

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> articleService.deleteArticleByAuthorAndSlug(userId, slug))
                .withMessageMatching(ErrorCode.USER_NOT_FOUND.getMessage());
        }

        @Test
        @DisplayName("Article이 존재하지 않으면 exception이 발생해야 한다.")
        void deleteArticleBySlugExceptionTest() {

            // given
            Article article = Article.from(articleContent, user);
            Long userId = 1L;
            Slug slug = article.slug();
            when(userService.findById(userId)).thenReturn(user);
            when(articleRepository.findByAuthorAndArticleContentSlugTitleSlug(user, slug)).thenReturn(Optional.empty());

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> articleService.deleteArticleByAuthorAndSlug(userId, slug))
                .withMessageMatching(ErrorCode.ARTICLE_NOT_FOUND_BY_AUTHOR_AND_SLUG.getMessage());
        }

        @Test
        @DisplayName("Article을 삭제할 수 있다.")
        void deleteArticleBySlugSuccessTest() {

            // given
            Article article = Article.from(articleContent, user);
            Long userId = 1L;
            Slug slug = article.slug();
            when(userService.findById(userId)).thenReturn(user);
            when(articleRepository.findByAuthorAndArticleContentSlugTitleSlug(user, slug))
                .thenReturn(Optional.of(article));
            OffsetDateTime start = OffsetDateTime.now();
            articleService.deleteArticleByAuthorAndSlug(userId, slug);
            OffsetDateTime end = OffsetDateTime.now();

            // when
            OffsetDateTime result = article.deletedAt();

            // then
            assertThat(result).isAfter(start).isBefore(end);
        }
    }

    @Test
    @DisplayName("원하는 offset, limit을 가진 Page 리스트를 반환할 수 있다.")
    void findAllArticlesByOffsetLimitTest() {

        // setup & given
        int offset = 0;
        int limit = 4;
        List<Article> articles = Arrays.asList(Article.from(articleContent, user),
            Article.from(articleContent, user),
            Article.from(articleContent, user),
            Article.from(articleContent, user),
            Article.from(articleContent, user)
        );
        PageRequest pageRequest = PageRequest.of(offset, limit);
        when(articleRepository.findPageByTagAndAuthor(pageRequest, null, null))
            .thenReturn(new PageImpl<>(articles.subList(0, 4), pageRequest, articles.size()));

        // when
        Page<Article> result = articleService.findAllArticles(pageRequest, null, null);

        // then
        assertAll(
            () -> assertThat(result.getSize()).isEqualTo(limit),
            () -> assertThat(result.getNumber()).isEqualTo(offset),
            () -> assertThat(result.getTotalPages()).isEqualTo(2)
        );
    }


}