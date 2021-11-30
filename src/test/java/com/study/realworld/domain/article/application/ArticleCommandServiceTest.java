package com.study.realworld.domain.article.application;

import com.study.realworld.domain.article.domain.persist.Article;
import com.study.realworld.domain.article.domain.persist.ArticleRepository;
import com.study.realworld.domain.article.dto.ArticleSave;
import com.study.realworld.domain.article.dto.ArticleUpdate;
import com.study.realworld.domain.article.strategy.SlugStrategy;
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
import java.util.List;

import static com.study.realworld.domain.article.util.ArticleFixture.*;
import static com.study.realworld.domain.user.util.UserFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
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

    @InjectMocks
    private ArticleCommandService articleCommandService;

    @Test
    void 게시글을_저장할_수_있다() {
        final User author = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        final Article article = createArticle(ARTICLE_SLUG, ARTICLE_TITLE, ARTICLE_BODY, ARTICLE_DESCRIPTION, author);
        final ArticleSave.Request request = createArticleSaveRequest(ARTICLE_TITLE, ARTICLE_BODY, ARTICLE_DESCRIPTION);

        ReflectionTestUtils.setField(article, "createdAt", LocalDateTime.now());
        ReflectionTestUtils.setField(article, "updatedAt", LocalDateTime.now());

        willReturn(article).given(articleRepository).save(any());
        willReturn(author).given(userQueryService).findById(any());
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
                () -> assertThat(response.tags()).isEqualTo(List.of("reactjs", "angularjs", "dragons")),
                () -> assertThat(response.author().userName()).isEqualTo(author.userName()),
                () -> assertThat(response.author().userBio()).isEqualTo(author.userBio()),
                () -> assertThat(response.author().userImage()).isEqualTo(author.userImage()),
                () -> assertThat(response.author().following()).isEqualTo(false)
        );
    }

    @Test
    void 저자_정보가_일치하지_않는다면_게시글을_변경_수_앖다() {
        final User loginUser = createUser(OTHER_USER_EMAIL, OTHER_USER_NAME, OTHER_USER_PASSWORD, OTHER_USER_BIO, OTHER_USER_IMAGE);
        final User author = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        final Article article = createArticle(ARTICLE_SLUG, ARTICLE_TITLE, ARTICLE_BODY, ARTICLE_DESCRIPTION, author);
        final ArticleUpdate.Request request = createArticleUpdateRequest(OTHER_ARTICLE_TITLE, OTHER_ARTICLE_BODY, OTHER_ARTICLE_DESCRIPTION);

        willReturn(loginUser).given(userQueryService).findById(any());
        willReturn(article.articleSlug().articleSlug()).given(slugStrategy).mapToSlug(any());

//        assertThatThrownBy(() -> articleCommandService.update(1L, article.articleSlug(), request))
//                .isExactlyInstanceOf()
//                .hasMessage();
    }
}
