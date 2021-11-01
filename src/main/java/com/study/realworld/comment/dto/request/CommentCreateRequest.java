package com.study.realworld.comment.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.study.realworld.comment.domain.CommentBody;

@JsonTypeName(value = "comment")
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class CommentCreateRequest {

    @JsonProperty("body")
    private String body;

    CommentCreateRequest() {
    }

    public CommentBody toCommentBody() {
        return CommentBody.of(body);
    }

}
