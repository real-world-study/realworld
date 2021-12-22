package com.study.realworld.domain.article.application;

import com.study.realworld.domain.article.domain.persist.Article;
import com.study.realworld.domain.article.domain.persist.ArticleQueryRepository;
import com.study.realworld.domain.article.dto.ArticleInfo;
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
import java.util.stream.Collectors;

import static com.study.realworld.domain.article.util.ArticleFixture.*;
import static com.study.realworld.domain.article.util.ArticleTagFixture.createArticleTag;
import static com.study.realworld.domain.tag.util.TagFixture.*;
import static com.study.realworld.domain.user.util.UserFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;

@DisplayName("게시글 유저 팔로우 쿼리 서비스(ArticleQueryService)")
@ExtendWith(MockitoExtension.class)
class ArticleQueryServiceTest {

    @Mock
    private UserQueryService userQueryService;

    @Mock
    private ArticleQueryRepository articleQueryRepository;

    @InjectMocks
    private ArticleQueryService articleQueryService;

    @Test
    void 유저_아이덴티티와_슬러그를_통해_게시글_정보_조회하기() {
        final User author = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        final Article article = createArticle(ARTICLE_SLUG, ARTICLE_TITLE, ARTICLE_BODY, ARTICLE_DESCRIPTION, author);
        article.addArticleTags(createTags(TAG_NAME_REACT_JS, TAG_NAME_ANGULAR_JS, TAG_NAME_DRAGONS).stream()
                .map(it -> createArticleTag(article, it))
                .collect(Collectors.toList()));

        ReflectionTestUtils.setField(article, "createdAt", LocalDateTime.now());
        ReflectionTestUtils.setField(article, "updatedAt", LocalDateTime.now());

        final ArticleInfo expected = new ArticleInfo(article, false, 0, false);

        willReturn(author).given(userQueryService).findById(any());
        willReturn(expected).given(articleQueryRepository).findByArticleSlug(any(), any());

        final ArticleInfo articleInfo = articleQueryService.findByArticleSlug(1L, article.articleSlug());
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

    @Test
    void 슬러그를_통해서만_게시글_정보_조회하기() {
        final User author = createUser(USER_EMAIL, USER_NAME, USER_PASSWORD, USER_BIO, USER_IMAGE);
        final Article article = createArticle(ARTICLE_SLUG, ARTICLE_TITLE, ARTICLE_BODY, ARTICLE_DESCRIPTION, author);
        article.addArticleTags(createTags(TAG_NAME_REACT_JS, TAG_NAME_ANGULAR_JS, TAG_NAME_DRAGONS).stream()
                .map(it -> createArticleTag(article, it))
                .collect(Collectors.toList()));

        ReflectionTestUtils.setField(article, "createdAt", LocalDateTime.now());
        ReflectionTestUtils.setField(article, "updatedAt", LocalDateTime.now());

        final ArticleInfo expected = new ArticleInfo(article, false, 0, false);

        willReturn(expected).given(articleQueryRepository).findByArticleSlug(any());
        final ArticleInfo articleInfo = articleQueryService.findByArticleSlug(article.articleSlug());

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
