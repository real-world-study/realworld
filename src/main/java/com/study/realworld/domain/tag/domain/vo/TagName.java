package com.study.realworld.domain.tag.domain.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class TagName {

    @NotBlank(message = "tagName must have not blank")
    @Column(name = "tag_name")
    private String tagName;

    public static TagName from(final String tagName) {
        return new TagName(tagName);
    }

    @JsonValue
    public String tagName() {
        return tagName;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final TagName tagName1 = (TagName) o;
        return Objects.equals(tagName(), tagName1.tagName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(tagName());
    }
}
