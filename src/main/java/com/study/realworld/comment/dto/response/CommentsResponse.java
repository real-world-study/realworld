package com.study.realworld.comment.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.realworld.comment.domain.Comment;
import com.study.realworld.comment.dto.response.CommentResponse.CommentResponseNested;
import com.study.realworld.user.domain.User;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CommentsResponse {

    @JsonProperty("comments")
    private List<CommentResponseNested> commentResponseNesteds;

    CommentsResponse() {
    }

    private CommentsResponse(List<CommentResponseNested> commentResponseNesteds) {
        this.commentResponseNesteds = commentResponseNesteds;
    }

    public static CommentsResponse fromComments(List<Comment> comments) {
        return new CommentsResponse(
            comments.stream()
                .map(CommentResponseNested::fromComment)
                .collect(Collectors.toList())
        );
    }

    public static CommentsResponse fromCommentsAndUser(List<Comment> comments, User user) {
        return new CommentsResponse(
            comments.stream()
                .map(comment -> CommentResponseNested.fromCommentAndUser(comment, user))
                .collect(Collectors.toList())
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
        CommentsResponse that = (CommentsResponse) o;
        return Objects.equals(commentResponseNesteds, that.commentResponseNesteds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentResponseNesteds);
    }

}
