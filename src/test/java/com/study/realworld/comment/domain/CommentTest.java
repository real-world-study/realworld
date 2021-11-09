package com.study.realworld.comment.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.study.realworld.article.domain.Article;
import com.study.realworld.article.domain.vo.ArticleContent;
import com.study.realworld.article.domain.vo.Body;
import com.study.realworld.article.domain.vo.Description;
import com.study.realworld.article.domain.vo.SlugTitle;
import com.study.realworld.article.domain.vo.Title;
import com.study.realworld.comment.domain.vo.CommentBody;
import com.study.realworld.global.exception.BusinessException;
import com.study.realworld.global.exception.ErrorCode;
import com.study.realworld.tag.domain.Tag;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.vo.Bio;
import com.study.realworld.user.domain.vo.Email;
import com.study.realworld.user.domain.vo.Image;
import com.study.realworld.user.domain.vo.Password;
import com.study.realworld.user.domain.vo.Username;
import java.time.OffsetDateTime;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CommentTest {

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

    @Test
    void commentTest() {
        Comment comment = new Comment();
    }

    @Nested
    @DisplayName("delteComment comment 삭제 테스트")
    class deleteCommentTest {

        @Test
        @DisplayName("삭제 요청 유저가 작성 유저와 다를 경우 exception이 발생해야 한다.")
        void deleteCommentExceptionTest() {

            // given
            Comment comment = Comment.from(commentBody, author, article);
            User user = User.Builder()
                .email(Email.of("email2@email2.com"))
                .password(Password.of("password"))
                .profile(Username.of("username2"), Bio.of("bio"), Image.of("image"))
                .build();

            // when & then
            assertThatExceptionOfType(BusinessException.class)
                .isThrownBy(() -> comment.deleteCommentByAuthor(user))
                .withMessageMatching(ErrorCode.INVALID_COMMENT_AUTHOR_DISMATCH.getMessage());
        }

        @Test
        @DisplayName("Comment를 삭제할 때 삭제 시간을 저장할 수 있다.")
        void deleteCommentSuccessTest() {

            // given
            Comment comment = Comment.from(commentBody, author, article);
            OffsetDateTime startTime = OffsetDateTime.now();
            comment.deleteCommentByAuthor(comment.author());
            OffsetDateTime endTime = OffsetDateTime.now();

            // when
            OffsetDateTime result = comment.deletedAt();

            // then
            assertThat(result).isAfter(startTime).isBefore(endTime);
        }

    }

    @Test
    @DisplayName("equals hashCode 테스트")
    void commentEqualsHashCodeTest() {

        // when
        Comment result = Comment.from(commentBody, author, article);

        // then
        assertThat(result)
            .isEqualTo(Comment.from(commentBody, author, article))
            .hasSameHashCodeAs(Comment.from(commentBody, author, article));
    }

}
