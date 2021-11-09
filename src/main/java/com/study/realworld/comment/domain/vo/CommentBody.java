package com.study.realworld.comment.domain.vo;

import static com.google.common.base.Preconditions.checkArgument;

import com.fasterxml.jackson.annotation.JsonValue;
import com.study.realworld.global.exception.ErrorCode;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CommentBody {

    @Column(name = "body", columnDefinition = "text", nullable = false)
    private String body;

    protected CommentBody() {
    }

    private CommentBody(String body) {
        this.body = body;
    }

    public static CommentBody of(String body) {
        checkBody(body);

        return new CommentBody(body);
    }

    public static void checkBody(String body) {
        checkArgument(Objects.nonNull(body), ErrorCode.INVALID_COMMENT_BODY_NULL);
    }

    @JsonValue
    public String body() {
        return body;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommentBody body1 = (CommentBody) o;
        return Objects.equals(body, body1.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body);
    }

}
