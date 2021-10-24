package com.study.realworld.article.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import com.study.realworld.article.domain.Article;
import com.study.realworld.article.domain.ArticleContent;
import com.study.realworld.article.domain.ArticleRepository;
import com.study.realworld.article.domain.Body;
import com.study.realworld.article.domain.Description;
import com.study.realworld.article.domain.Slug;
import com.study.realworld.article.domain.SlugTitle;
import com.study.realworld.article.domain.Title;
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
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    @DisplayName("deleteARticleBySlug Article 삭제 테스트")
    class deleteArticleBySlug {

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
                .isThrownBy(() -> articleService.deleteArticleBySlug(userId, slug))
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
                .isThrownBy(() -> articleService.deleteArticleBySlug(userId, slug))
                .withMessageMatching(ErrorCode.ARTICLE_NOT_FOUND_BY_SLUG.getMessage());
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
            articleService.deleteArticleBySlug(userId, slug);
            OffsetDateTime end = OffsetDateTime.now();

            // when
            OffsetDateTime result = article.deletedAt();

            // then
            assertThat(result).isAfter(start).isBefore(end);
        }
    }

}