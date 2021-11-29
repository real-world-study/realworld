package com.study.realworld.domain.article.application;

import com.study.realworld.domain.article.domain.persist.Article;
import com.study.realworld.domain.article.dto.ArticleInfo;
import com.study.realworld.domain.follow.application.FollowQueryService;
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

import static com.study.realworld.domain.article.domain.persist.ArticleTest.testArticle;
import static com.study.realworld.domain.user.domain.persist.UserTest.testDefaultUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;

@DisplayName("게시글 유저 팔로우 쿼리 서비스(ArticleUserFollowQueryService)")
@ExtendWith(MockitoExtension.class)
class ArticleUserFollowQueryServiceTest {

    @Mock
    private ArticleQueryService articleQueryService;

    @Mock
    private UserQueryService userQueryService;

    @Mock
    private FollowQueryService followQueryService;

    @InjectMocks
    private ArticleUserFollowQueryService articleUserFollowQueryService;

    @Test
    void 유저_아이덴티티와_슬러그를_통해_게시글_정보_조회하기() {
        final User user = testDefaultUser();
        final Article article = testArticle();
        ReflectionTestUtils.setField(article, "createdAt", LocalDateTime.now());
        ReflectionTestUtils.setField(article, "updatedAt", LocalDateTime.now());

        willReturn(user).given(userQueryService).findById(any());
        willReturn(true).given(followQueryService).existsByFolloweeAndFollower(any(), any());
        willReturn(article).given(articleQueryService).findByArticleSlug(any());

        final ArticleInfo articleInfo = articleUserFollowQueryService.findByArticleSlug(1L, article.articleSlug());
        assertAll(
                () -> assertThat(articleInfo.articleSlug()).isEqualTo(article.articleSlug()),
                () -> assertThat(articleInfo.articleTitle()).isEqualTo(article.articleTitle()),
                () -> assertThat(articleInfo.articleBody()).isEqualTo(article.articleBody()),
                () -> assertThat(articleInfo.articleDescription()).isEqualTo(article.articleDescription()),
                () -> assertThat(articleInfo.author().userName()).isEqualTo(article.author().userName()),
                () -> assertThat(articleInfo.author().userBio()).isEqualTo(article.author().userBio()),
                () -> assertThat(articleInfo.author().userImage()).isEqualTo(article.author().userImage()),
                () -> assertThat(articleInfo.author().following()).isTrue()
        );
    }

    @Test
    void 슬러그를_통해서만_게시글_정보_조회하기() {
        final Article article = testArticle();
        ReflectionTestUtils.setField(article, "createdAt", LocalDateTime.now());
        ReflectionTestUtils.setField(article, "updatedAt", LocalDateTime.now());

        willReturn(article).given(articleQueryService).findByArticleSlug(any());

        final ArticleInfo articleInfo = articleUserFollowQueryService.findByArticleSlugAndExcludeUser(article.articleSlug());
        assertAll(
                () -> assertThat(articleInfo.articleSlug()).isEqualTo(article.articleSlug()),
                () -> assertThat(articleInfo.articleTitle()).isEqualTo(article.articleTitle()),
                () -> assertThat(articleInfo.articleBody()).isEqualTo(article.articleBody()),
                () -> assertThat(articleInfo.articleDescription()).isEqualTo(article.articleDescription()),
                () -> assertThat(articleInfo.author().userName()).isEqualTo(article.author().userName()),
                () -> assertThat(articleInfo.author().userBio()).isEqualTo(article.author().userBio()),
                () -> assertThat(articleInfo.author().userImage()).isEqualTo(article.author().userImage()),
                () -> assertThat(articleInfo.author().following()).isFalse()
        );
    }
}
