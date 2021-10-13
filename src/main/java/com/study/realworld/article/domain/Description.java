package com.study.realworld.article.domain;

import static com.google.common.base.Preconditions.checkArgument;

import com.study.realworld.global.exception.ErrorCode;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.apache.commons.lang3.StringUtils;

@Embeddable
public class Description {

    @Column(name = "description", length = 255, nullable = false)
    private String description;

    protected Description() {
    }

    private Description(String description) {
        this.description = description;
    }

    public static Description of(String description) {
        checkDescription(description);

        return new Description(description);
    }

    private static void checkDescription(String description) {
        checkArgument(StringUtils.isNotBlank(description), ErrorCode.INVALID_DESCRIPTION_NULL);
        checkArgument(description.length() <= 255, ErrorCode.INVALID_DESCRIPTION_LENGTH);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Description that = (Description) o;
        return Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description);
    }

}
