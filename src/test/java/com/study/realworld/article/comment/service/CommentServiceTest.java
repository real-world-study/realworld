package com.study.realworld.article.comment.service;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import com.study.realworld.article.comment.domain.CommentBody;
import com.study.realworld.article.comment.domain.CommentRepository;
import com.study.realworld.article.domain.Article;
import com.study.realworld.article.domain.ArticleContent;
import com.study.realworld.article.domain.Body;
import com.study.realworld.article.domain.Description;
import com.study.realworld.article.domain.SlugTitle;
import com.study.realworld.article.domain.Title;
import com.study.realworld.article.service.ArticleService;
import com.study.realworld.global.exception.BusinessException;
import com.study.realworld.global.exception.ErrorCode;
import com.study.realworld.tag.domain.Tag;
import com.study.realworld.user.domain.Bio;
import com.study.realworld.user.domain.Email;
import com.study.realworld.user.domain.Image;
import com.study.realworld.user.domain.Password;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.Username;
import com.study.realworld.user.service.UserService;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserService userService;

    @Mock
    private ArticleService articleService;

    @InjectMocks
    private CommentService commentService;

    private User author;
    private Article article;
    private CommentBody commentBody;

    @BeforeEach
    void beforeEach() {
        author = User.Builder()
            .email(Email.of("email@email.com"))
            .password(Password.of("password"))
            .profile(Username.of("username"), Bio.of("bio"), Image.of("image"))
            .build();
        ArticleContent articleContent = ArticleContent.builder()
            .slugTitle(SlugTitle.of(Title.of("title")))
            .description(Description.of("description"))
            .body(Body.of("body"))
            .tags(Arrays.asList(Tag.of("tag1"), Tag.of("tag2")))
            .build();
        article = Article.from(articleContent, author);
        commentBody = CommentBody.of("comment body");
    }

    @Nested
    @DisplayName("createComment Comment 저장 기능 테스트")
    class createCommentTest {

        @Test
        @DisplayName("로그인 유저가 없는 유저일 때 exception이 발생해야 한다.")
        void createCommentExceptionByNotFoundUser() {

            // setup & given
            Long userId = 1L;
            when(userService.findById(userId)).thenThrow(new BusinessException(ErrorCode.USER_NOT_FOUND));

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> commentService.createComment(userId, null, null))
                .withMessageMatching(ErrorCode.USER_NOT_FOUND.getMessage());
        }

    }

}