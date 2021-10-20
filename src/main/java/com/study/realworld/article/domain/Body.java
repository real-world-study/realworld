package com.study.realworld.article.domain;

import static com.google.common.base.Preconditions.checkArgument;

import com.fasterxml.jackson.annotation.JsonValue;
import com.study.realworld.global.exception.ErrorCode;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Body {

    @Column(name = "body", columnDefinition = "text", nullable = false)
    private String body;

    protected Body() {
    }

    private Body(String body) {
        this.body = body;
    }

    public static Body of(String body) {
        checkBody(body);

        return new Body(body);
    }

    public static void checkBody(String body) {
        checkArgument(Objects.nonNull(body), ErrorCode.INVALID_BODY_NULL);
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
        Body body1 = (Body) o;
        return Objects.equals(body, body1.body);
    }

    @Override
    public int hashCode() {
        return Objects.hash(body);
    }

}
