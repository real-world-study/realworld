package com.study.realworld.domain.article.application;

import com.study.realworld.domain.article.domain.persist.Article;
import com.study.realworld.domain.article.domain.persist.ArticleRepository;
import com.study.realworld.domain.user.application.UserQueryService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static com.study.realworld.domain.article.domain.persist.ArticleTest.testArticle;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willReturn;

@DisplayName("게시글 쿼리 서비스(ArticleQueryService)")
@ExtendWith(MockitoExtension.class)
class ArticleQueryServiceTest {

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleQueryService articleQueryService;

    @Test
    void 슬러그로_게시글_찾기() {
        final Article article = testArticle();
        ReflectionTestUtils.setField(article, "articleId", 1L);
        willReturn(Optional.of(article)).given(articleRepository).findByArticleSlug(any());

        final Article findArticle = articleQueryService.findByArticleSlug(article.articleSlug());
        assertAll(
                () -> assertThat(findArticle.articleId()).isEqualTo(1L),
                () -> assertThat(findArticle.articleSlug()).isEqualTo(article.articleSlug()),
                () -> assertThat(findArticle.articleTitle()).isEqualTo(article.articleTitle()),
                () -> assertThat(findArticle.articleBody()).isEqualTo(article.articleBody()),
                () -> assertThat(findArticle.articleDescription()).isEqualTo(article.articleDescription()),
                () -> assertThat(findArticle.author()).isEqualTo(article.author())
        );
    }
}
