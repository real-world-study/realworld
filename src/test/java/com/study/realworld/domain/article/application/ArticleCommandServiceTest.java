package com.study.realworld.domain.article.application;

import com.study.realworld.domain.article.domain.persist.Article;
import com.study.realworld.domain.article.domain.persist.ArticleRepository;
import com.study.realworld.domain.user.application.UserQueryService;
import com.study.realworld.domain.user.domain.persist.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static com.study.realworld.domain.article.domain.persist.ArticleTest.testArticle;
import static com.study.realworld.domain.user.domain.persist.UserTest.testDefaultUser;
import static org.assertj.core.api.Assertions.assertThat;
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

        final Article entity = testArticle();
        entity.changeAuthor(user);
        ReflectionTestUtils.setField(entity, "articleId", 1L);
        willReturn(entity).given(articleRepository).save(any());

        final Article article = articleCommandService.save(1L, entity);
        assertThat(article).isEqualTo(entity);
    }
}