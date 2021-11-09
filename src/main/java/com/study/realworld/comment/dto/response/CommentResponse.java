package com.study.realworld.comment.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.realworld.comment.domain.Comment;
import com.study.realworld.comment.domain.vo.CommentBody;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.dto.response.ProfileResponse.ProfileResponseNested;
import java.time.OffsetDateTime;
import java.util.Objects;

public class CommentResponse {

    @JsonProperty("comment")
    private CommentResponseNested commentResponseNested;

    CommentResponse() {
    }

    private CommentResponse(CommentResponseNested commentResponseNested) {
        this.commentResponseNested = commentResponseNested;
    }

    public static CommentResponse fromComment(Comment comment) {
        return new CommentResponse(CommentResponseNested.fromComment(comment));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommentResponse that = (CommentResponse) o;
        return Objects.equals(commentResponseNested, that.commentResponseNested);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentResponseNested);
    }

    public static class CommentResponseNested {

        @JsonProperty("id")
        private Long id;

        @JsonProperty("createdAt")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
        private OffsetDateTime createdAt;

        @JsonProperty("updatedAt")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
        private OffsetDateTime updatedAt;

        @JsonProperty("body")
        private CommentBody commentBody;

        @JsonProperty("author")
        private ProfileResponseNested profileResponseNested;

        CommentResponseNested() {
        }

        public CommentResponseNested(Long id, OffsetDateTime createdAt, OffsetDateTime updatedAt,
            CommentBody commentBody,
            ProfileResponseNested profileResponseNested) {
            this.id = id;
            this.createdAt = createdAt;
            this.updatedAt = updatedAt;
            this.commentBody = commentBody;
            this.profileResponseNested = profileResponseNested;
        }

        public static CommentResponseNested fromComment(Comment comment) {
            return new CommentResponseNested(
                comment.id(),
                comment.createdAt(),
                comment.updatedAt(),
                comment.commentBody(),
                ProfileResponseNested.fromProfileAndFollowing(comment.author().profile(), false)
            );
        }

        public static CommentResponseNested fromCommentAndUser(Comment comment, User user) {
            return new CommentResponseNested(
                comment.id(),
                comment.createdAt(),
                comment.updatedAt(),
                comment.commentBody(),
                ProfileResponseNested.fromProfileAndFollowing(comment.author().profile(), user.isFollow(comment.author()))
            );
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            CommentResponseNested that = (CommentResponseNested) o;
            return Objects.equals(id, that.id) && Objects.equals(createdAt, that.createdAt) && Objects
                .equals(updatedAt, that.updatedAt) && Objects.equals(commentBody, that.commentBody) && Objects
                .equals(profileResponseNested, that.profileResponseNested);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, createdAt, updatedAt, commentBody, profileResponseNested);
        }

    }
}
