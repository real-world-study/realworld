package com.study.realworld.comment.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.realworld.comment.controller.response.CommentResponse.CommentResponseNested;
import com.study.realworld.comment.domain.Comment;
import java.util.List;
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

}
