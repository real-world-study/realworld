package com.study.realworld.comment.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import com.study.realworld.article.domain.Article;
import com.study.realworld.article.domain.vo.ArticleContent;
import com.study.realworld.article.domain.vo.Body;
import com.study.realworld.article.domain.vo.Description;
import com.study.realworld.article.domain.vo.Slug;
import com.study.realworld.article.domain.vo.SlugTitle;
import com.study.realworld.article.domain.vo.Title;
import com.study.realworld.article.service.ArticleService;
import com.study.realworld.comment.domain.Comment;
import com.study.realworld.comment.domain.CommentRepository;
import com.study.realworld.comment.domain.vo.CommentBody;
import com.study.realworld.comment.dto.response.CommentResponse;
import com.study.realworld.comment.dto.response.CommentsResponse;
import com.study.realworld.global.exception.BusinessException;
import com.study.realworld.global.exception.ErrorCode;
import com.study.realworld.tag.domain.Tag;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.vo.Bio;
import com.study.realworld.user.domain.vo.Email;
import com.study.realworld.user.domain.vo.Follows;
import com.study.realworld.user.domain.vo.Password;
import com.study.realworld.user.domain.vo.Username;
import com.study.realworld.user.service.UserService;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.HashSet;
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
            .profile(Username.of("jake"), Bio.of("I work at statefarm"), null)
            .email(Email.of("jake@jake.jake"))
            .password(Password.of("jakejake"))
            .follows(Follows.of(new HashSet<>()))
            .build();
        ArticleContent articleContent = ArticleContent.builder()
            .slugTitle(SlugTitle.of(Title.of("How to train your dragon")))
            .description(Description.of("Ever wonder how?"))
            .body(Body.of("It takes a Jacobian"))
            .tags(Arrays.asList(Tag.of("dragons"), Tag.of("training")))
            .build();
        article = Article.from(articleContent, author);
        commentBody = CommentBody.of("It takes a Jacobian");
    }

    @Nested
    @DisplayName("createComment Comment 저장 기능 테스트")
    class createCommentTest {

        @Test
        @DisplayName("로그인 유저가 없는 유저일 때 exception이 발생해야 한다.")
        void createCommentExceptionByNotFoundUserTest() {

            // setup & given
            Long userId = 1L;
            when(userService.findById(userId)).thenThrow(new BusinessException(ErrorCode.USER_NOT_FOUND));

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> commentService.createComment(userId, null, null))
                .withMessageMatching(ErrorCode.USER_NOT_FOUND.getMessage());
        }

        @Test
        @DisplayName("게시글이 없는 게시글일 때 exception이 발생해야 한다.")
        void createCommentExceptionByNotFoundArticleTest() {

            // setup & given
            Long userId = 1L;
            Slug slug = Slug.of("how-to-train-your-dragon");
            when(userService.findById(userId)).thenReturn(author);
            when(articleService.findBySlug(slug)).thenThrow(new BusinessException(ErrorCode.ARTICLE_NOT_FOUND_BY_SLUG));

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> commentService.createComment(userId, slug, null))
                .withMessageMatching(ErrorCode.ARTICLE_NOT_FOUND_BY_SLUG.getMessage());
        }

        @Test
        @DisplayName("comment를 생성할 수 있다.")
        void createCommentSuccessTest() {

            // setup & given
            Long userId = 1L;
            Slug slug = Slug.of("how-to-train-your-dragon");
            when(userService.findById(userId)).thenReturn(author);
            when(articleService.findBySlug(slug)).thenReturn(article);
            Comment comment = Comment.from(commentBody, author, article);
            when(commentRepository.save(comment)).thenReturn(comment);

            CommentResponse expected = CommentResponse.fromComment(comment);

            // when
            CommentResponse result = commentService.createComment(userId, slug, commentBody);

            // then
            assertThat(result).isEqualTo(expected);
        }

    }

    @Nested
    @DisplayName("getCommentsByArticleSlug comments 반환 테스트")
    class getCommentsByArticleSlugTest {

        @Test
        @DisplayName("게시글이 없는 게시글일 때 exception이 발생해야 한다.")
        void getCommentsByArticleSlugExceptionByNotFoundArticleTest() {

            // setup & given
            Slug slug = Slug.of("how-to-train-your-dragon");
            when(articleService.findBySlug(slug)).thenThrow(new BusinessException(ErrorCode.ARTICLE_NOT_FOUND_BY_SLUG));

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> commentService.findCommentsByArticleSlug(slug))
                .withMessageMatching(ErrorCode.ARTICLE_NOT_FOUND_BY_SLUG.getMessage());
        }

        @Test
        @DisplayName("comments를 반환할 수 있다.")
        void getCommentsByArticleSlugTest() {

            // given
            Slug slug = Slug.of("how-to-train-your-dragon");
            when(articleService.findBySlug(slug)).thenReturn(article);
            List<Comment> comments = Arrays.asList(Comment.from(commentBody, author, article),
                Comment.from(commentBody, author, article),
                Comment.from(commentBody, author, article));
            when(commentRepository.findAllByArticle(article)).thenReturn(comments);

            CommentsResponse expected = CommentsResponse.fromComments(comments);

            // when
            CommentsResponse result = commentService.findCommentsByArticleSlug(slug);

            // then
            assertThat(result).isEqualTo(expected);
        }

    }

    @Nested
    @DisplayName("getCommentsByArticleSlug comments 반환 테스트 (parameter += Login user")
    class getCommentsByArticleSlugAndLoginUserTest {

        @Test
        @DisplayName("없는 유저일 때 exception이 발생해야 한다.")
        void getCommentsByArticleSlugExceptionByNotFoundUserTest() {

            // setup & given
            Long userId = 1L;
            Slug slug = Slug.of("how-to-train-your-dragon");
            when(userService.findById(userId)).thenThrow(new BusinessException(ErrorCode.USER_NOT_FOUND));

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> commentService.findCommentsByArticleSlug(userId, slug))
                .withMessageMatching(ErrorCode.USER_NOT_FOUND.getMessage());
        }

        @Test
        @DisplayName("게시글이 없는 게시글일 때 exception이 발생해야 한다.")
        void getCommentsByArticleSlugExceptionByNotFoundArticleTest() {

            // setup & given
            Long userId = 1L;
            when(userService.findById(userId)).thenReturn(author);
            Slug slug = Slug.of("how-to-train-your-dragon");
            when(articleService.findBySlug(slug)).thenThrow(new BusinessException(ErrorCode.ARTICLE_NOT_FOUND_BY_SLUG));

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> commentService.findCommentsByArticleSlug(userId, slug))
                .withMessageMatching(ErrorCode.ARTICLE_NOT_FOUND_BY_SLUG.getMessage());
        }

        @Test
        @DisplayName("comments를 반환할 수 있다.")
        void getCommentsByArticleUnfollowingSlugTest() {

            // setup & given
            Long userId = 1L;
            when(userService.findById(userId)).thenReturn(author);
            Slug slug = Slug.of("how-to-train-your-dragon");
            when(articleService.findBySlug(slug)).thenReturn(article);
            List<Comment> comments = Arrays.asList(Comment.from(commentBody, author, article),
                Comment.from(commentBody, author, article),
                Comment.from(commentBody, author, article));
            when(commentRepository.findAllByArticle(article)).thenReturn(comments);

            CommentsResponse expected = CommentsResponse.fromCommentsAndUser(comments, author);

            // when
            CommentsResponse result = commentService.findCommentsByArticleSlug(userId, slug);

            // then
            assertThat(result).isEqualTo(expected);
        }

    }

    @Nested
    @DisplayName("deleteCommentByCommentId Comment 삭제 기능 테스트")
    class deleteCommentByCommentIdTest {

        @Test
        @DisplayName("로그인 유저가 없는 유저일 때 exception이 발생해야 한다.")
        void deleteCommentByCommentIdExceptionByNotFoundUserTest() {

            // setup & given
            Long userId = 1L;
            when(userService.findById(userId)).thenThrow(new BusinessException(ErrorCode.USER_NOT_FOUND));

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> commentService.deleteCommentByCommentId(userId, null, null))
                .withMessageMatching(ErrorCode.USER_NOT_FOUND.getMessage());
        }

        @Test
        @DisplayName("게시글이 없는 게시글일 때 exception이 발생해야 한다.")
        void deleteCommentByCommentIdExceptionByNotFoundArticleTest() {

            // setup & given
            Long userId = 1L;
            Slug slug = Slug.of("how-to-train-your-dragon");
            when(userService.findById(userId)).thenReturn(author);
            when(articleService.findBySlug(slug)).thenThrow(new BusinessException(ErrorCode.ARTICLE_NOT_FOUND_BY_SLUG));

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> commentService.deleteCommentByCommentId(userId, slug, null))
                .withMessageMatching(ErrorCode.ARTICLE_NOT_FOUND_BY_SLUG.getMessage());
        }

        @Test
        @DisplayName("없는 comment일 때 exception이 발생해야 한다.")
        void deleteCommentByCommentIdExceptionByNotFoundComment() {

            // setup & given
            Long userId = 1L;
            Slug slug = Slug.of("how-to-train-your-dragon");
            Long commentId = 2L;
            when(userService.findById(userId)).thenReturn(author);
            when(articleService.findBySlug(slug)).thenReturn(article);
            when(commentRepository.findByIdAndArticle(commentId, article))
                .thenThrow(new BusinessException(ErrorCode.INVALID_COMMENT_NOT_FOUND));

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> commentService.deleteCommentByCommentId(userId, slug, commentId))
                .withMessageMatching(ErrorCode.INVALID_COMMENT_NOT_FOUND.getMessage());
        }

        @Test
        @DisplayName("comment 작성자와 유저가 다를 때 exception이 발생해야 한다.")
        void deleteCommentByCommentIdExceptionByMissMatchUser() {

            // setup & given
            Long userId = 1L;
            User user = User.Builder()
                .email(Email.of("email1@email1.com"))
                .build();
            Slug slug = Slug.of("how-to-train-your-dragon");
            Long commentId = 2L;
            Comment comment = Comment.from(commentBody, author, article);
            when(userService.findById(userId)).thenReturn(user);
            when(articleService.findBySlug(slug)).thenReturn(article);
            when(commentRepository.findByIdAndArticle(commentId, article)).thenReturn(Optional.of(comment));

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> commentService.deleteCommentByCommentId(userId, slug, commentId))
                .withMessageMatching(ErrorCode.INVALID_COMMENT_AUTHOR_DISMATCH.getMessage());
        }

        @Test
        @DisplayName("comment를 제거할 수 있다.")
        void deleteCommentByCommentIdSuccessTest() {

            // setup & given
            Long userId = 1L;
            Slug slug = Slug.of("how-to-train-your-dragon");
            Long commentId = 2L;
            Comment comment = Comment.from(commentBody, author, article);
            when(userService.findById(userId)).thenReturn(author);
            when(articleService.findBySlug(slug)).thenReturn(article);
            when(commentRepository.findByIdAndArticle(commentId, article)).thenReturn(Optional.of(comment));

            OffsetDateTime start = OffsetDateTime.now();
            commentService.deleteCommentByCommentId(userId, slug, commentId);
            OffsetDateTime end = OffsetDateTime.now();

            // when
            OffsetDateTime result = comment.deletedAt();

            // then
            assertThat(result).isAfter(start).isBefore(end);
        }

    }

}