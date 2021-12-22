package com.study.realworld.domain.article.application;

import com.study.realworld.domain.article.domain.persist.Article;
import com.study.realworld.domain.article.domain.persist.ArticleRepository;
import com.study.realworld.domain.article.dto.ArticleSave;
import com.study.realworld.domain.article.dto.ArticleUpdate;
import com.study.realworld.domain.article.error.exception.AuthorMissMatchException;
import com.study.realworld.domain.article.strategy.SlugStrategy;
import com.study.realworld.domain.tag.application.TagCommandService;
import com.study.realworld.domain.user.application.UserQueryService;
import com.study.realworld.domain.user.domain.persist.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.study.realworld.domain.article.util.ArticleFixture.*;
import static com.study.realworld.domain.article.util.ArticleTagFixture.createArticleTag;
import static com.study.realworld.domain.tag.util.TagFixture.*;
import static com.study.realworld.domain.user.util.UserFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;

@DisplayName("게시글 커멘드 서비스(ArticleCommandService)")
@ExtendWith(MockitoExtension.class)
class ArticleCommandServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @Mock
    private SlugStrategy slugStrategy;

    @Mock
    private UserQueryService userQueryService;

    @Mock
    private TagCommandService tagCommandService;

    @InjectMocks
    private ArticleCommandService articleCommandService;

    @Test
    void 게시글을_저장할_수_있다() {
        final User author = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        final Article article = createArticle(ARTICLE_SLUG, ARTICLE_TITLE, ARTICLE_BODY, ARTICLE_DESCRIPTION, author);
        final ArticleSave.Request request = createArticleSaveRequest(ARTICLE_TITLE, ARTICLE_BODY, ARTICLE_DESCRIPTION);

        article.addArticleTags(createTags(TAG_NAME_REACT_JS, TAG_NAME_ANGULAR_JS, TAG_NAME_DRAGONS).stream()
                .map(it -> createArticleTag(article, it))
                .collect(Collectors.toList()));
        ReflectionTestUtils.setField(article, "createdAt", LocalDateTime.now());
        ReflectionTestUtils.setField(article, "updatedAt", LocalDateTime.now());

        willReturn(article).given(articleRepository).save(any());
        willReturn(author).given(userQueryService).findById(any());
        willReturn(createTag(TAG_NAME_REACT_JS))
                .willReturn(createTag(TAG_NAME_ANGULAR_JS))
                .willReturn(createTag(TAG_NAME_DRAGONS))
                .given(tagCommandService).save(any());

        willReturn(article.articleSlug().articleSlug()).given(slugStrategy).mapToSlug(any());

        final ArticleSave.Response response = ArticleSave.Response.from(articleCommandService.save(1L, request));
        assertAll(
                () -> assertThat(response.articleSlug()).isEqualTo(article.articleSlug()),
                () -> assertThat(response.articleTitle()).isEqualTo(article.articleTitle()),
                () -> assertThat(response.articleBody()).isEqualTo(article.articleBody()),
                () -> assertThat(response.articleDescription()).isEqualTo(article.articleDescription()),
                () -> assertThat(response.createdAt()).isNotNull(),
                () -> assertThat(response.updatedAt()).isNotNull(),
                () -> assertThat(response.favorited()).isFalse(),
                () -> assertThat(response.favoritesCount()).isZero(),
                () -> assertThat(response.tags()).isEqualTo(TAG_NAMES),
                () -> assertThat(response.author().userName()).isEqualTo(author.userName()),
                () -> assertThat(response.author().userBio()).isEqualTo(author.userBio()),
                () -> assertThat(response.author().userImage()).isEqualTo(author.userImage()),
                () -> assertThat(response.author().following()).isEqualTo(false)
        );
    }

    @Test
    void 저자는_게시글을_변경_수_있다() {
        final User author = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        final Article article = createArticle(ARTICLE_SLUG, ARTICLE_TITLE, ARTICLE_BODY, ARTICLE_DESCRIPTION, author);
        final ArticleUpdate.Request request = createArticleUpdateRequest(OTHER_ARTICLE_TITLE, OTHER_ARTICLE_BODY, OTHER_ARTICLE_DESCRIPTION);
        article.addArticleTags(createTags(TAG_NAME_REACT_JS, TAG_NAME_ANGULAR_JS, TAG_NAME_DRAGONS).stream()
                .map(it -> createArticleTag(article, it))
                .collect(Collectors.toList()));

        ReflectionTestUtils.setField(author, "userId", 1L);
        ReflectionTestUtils.setField(article, "articleId", 1L);
        ReflectionTestUtils.setField(article, "createdAt", LocalDateTime.now());
        ReflectionTestUtils.setField(article, "updatedAt", LocalDateTime.now());

        willReturn(author).given(userQueryService).findById(any());
        willReturn(Optional.of(article)).given(articleRepository).findByArticleSlug(any());
        willReturn(OTHER_ARTICLE_SLUG.articleSlug()).given(slugStrategy).mapToSlug(any());

        final ArticleUpdate.Response response = articleCommandService.update(1L, article.articleSlug(), request);
        assertAll(
                () -> assertThat(response.articleSlug()).isEqualTo(OTHER_ARTICLE_SLUG),
                () -> assertThat(response.articleTitle()).isEqualTo(OTHER_ARTICLE_TITLE),
                () -> assertThat(response.articleBody()).isEqualTo(OTHER_ARTICLE_BODY),
                () -> assertThat(response.articleDescription()).isEqualTo(OTHER_ARTICLE_DESCRIPTION),
                () -> assertThat(response.createdAt()).isNotNull(),
                () -> assertThat(response.updatedAt()).isNotNull(),
                () -> assertThat(response.favorited()).isFalse(),
                () -> assertThat(response.favoritesCount()).isZero(),
                () -> assertThat(response.tags()).isEqualTo(TAG_NAMES),
                () -> assertThat(response.author().userName()).isEqualTo(author.userName()),
                () -> assertThat(response.author().userBio()).isEqualTo(author.userBio()),
                () -> assertThat(response.author().userImage()).isEqualTo(author.userImage()),
                () -> assertThat(response.author().following()).isEqualTo(false)
        );
    }

    @Test
    void 저자_정보가_일치하지_않는다면_게시글을_변경_수_없다() {
        final User loginUser = createUser(OTHER_USER_EMAIL, OTHER_USER_NAME, OTHER_USER_PASSWORD, OTHER_USER_BIO, OTHER_USER_IMAGE);
        final User author = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        final Article article = createArticle(ARTICLE_SLUG, ARTICLE_TITLE, ARTICLE_BODY, ARTICLE_DESCRIPTION, author);
        final ArticleUpdate.Request request = createArticleUpdateRequest(OTHER_ARTICLE_TITLE, OTHER_ARTICLE_BODY, OTHER_ARTICLE_DESCRIPTION);

        ReflectionTestUtils.setField(loginUser, "userId", 1L);
        ReflectionTestUtils.setField(author, "userId", 2L);

        willReturn(loginUser).given(userQueryService).findById(any());
        willReturn(Optional.of(article)).given(articleRepository).findByArticleSlug(any());

        assertThatThrownBy(() -> articleCommandService.update(1L, article.articleSlug(), request))
                .isExactlyInstanceOf(AuthorMissMatchException.class)
                .hasMessage("수정자와 저자가 다릅니다");
    }

    @Test
    void 저자는_게시글을_삭제할_수_있다() {
        final User author = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        final Article article = createArticle(ARTICLE_SLUG, ARTICLE_TITLE, ARTICLE_BODY, ARTICLE_DESCRIPTION, author);

        willReturn(author).given(userQueryService).findById(any());
        willReturn(Optional.of(article)).given(articleRepository).findByArticleSlug(any());

        articleCommandService.delete(1L, article.articleSlug());
    }

    @Test
    void 저자가_아니면_게시글을_삭제할_수_없다() {
        final User loginUser = createUser(OTHER_USER_EMAIL, OTHER_USER_NAME, OTHER_USER_PASSWORD, OTHER_USER_BIO, OTHER_USER_IMAGE);
        final User author = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        final Article article = createArticle(ARTICLE_SLUG, ARTICLE_TITLE, ARTICLE_BODY, ARTICLE_DESCRIPTION, author);

        ReflectionTestUtils.setField(loginUser, "userId", 1L);
        ReflectionTestUtils.setField(author, "userId", 2L);

        willReturn(loginUser).given(userQueryService).findById(any());
        willReturn(Optional.of(article)).given(articleRepository).findByArticleSlug(any());

        assertThatThrownBy(() -> articleCommandService.delete(1L, article.articleSlug()))
                .isExactlyInstanceOf(AuthorMissMatchException.class)
                .hasMessage("수정자와 저자가 다릅니다");
    }
}
