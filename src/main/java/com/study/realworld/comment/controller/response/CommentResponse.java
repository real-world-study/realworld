package com.study.realworld.comment.controller.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.realworld.comment.domain.Comment;
import com.study.realworld.comment.domain.CommentBody;
import com.study.realworld.user.controller.response.ProfileResponse.ProfileResponseNested;
import java.time.OffsetDateTime;

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
                ProfileResponseNested.ofProfile(comment.author().profile())
            );
        }
    }
}
