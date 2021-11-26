package com.study.realworld.domain.article.application;

import com.study.realworld.domain.article.domain.persist.Article;
import com.study.realworld.domain.article.domain.persist.ArticleRepository;
import com.study.realworld.domain.article.dto.ArticleSave;
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

import static com.study.realworld.domain.article.domain.persist.ArticleTest.testArticle;
import static com.study.realworld.domain.user.domain.persist.UserTest.testDefaultUser;
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
    private UserQueryService userQueryService;

    @InjectMocks
    private ArticleCommandService articleCommandService;

    @Test
    void 게시글을_저장할_수_있다() {
        final User user = testDefaultUser();
        ReflectionTestUtils.setField(user, "userId", 1L);
        willReturn(user).given(userQueryService).findById(any());

        final Article article = testArticle();
        article.changeAuthor(user);
        ReflectionTestUtils.setField(article, "articleId", 1L);
        ReflectionTestUtils.setField(article, "createdAt", LocalDateTime.now());
        ReflectionTestUtils.setField(article, "updatedAt", LocalDateTime.now());
        willReturn(article).given(articleRepository).save(any());

        final ArticleSave.Response response = ArticleSave.Response.from(articleCommandService.save(1L, article));
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
                () -> assertThat(response.author().userName()).isEqualTo(user.userName()),
                () -> assertThat(response.author().userBio()).isEqualTo(user.userBio()),
                () -> assertThat(response.author().userImage()).isEqualTo(user.userImage()),
                () -> assertThat(response.author().following()).isEqualTo(false)
        );
    }
}
