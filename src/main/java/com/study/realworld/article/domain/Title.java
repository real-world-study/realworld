package com.study.realworld.article.domain;

import static com.google.common.base.Preconditions.checkArgument;

import com.fasterxml.jackson.annotation.JsonValue;
import com.study.realworld.global.exception.ErrorCode;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.apache.commons.lang3.StringUtils;

@Embeddable
public class Title {

    private static final String HYPHEN = "-";
    private static final String BLANK = " ";

    private static final String NONLATIN_REGEX = "[^\\w-]";
    private static final String WHITESPACE_REGEX = "\\s+";

    @Column(name = "title", length = 50, nullable = false)
    private String title;

    protected Title() {
    }

    private Title(String title) {
        this.title = title;
    }

    public static Title of(String title) {
        checkTitle(title);

        return new Title(title);
    }

    private static void checkTitle(String title) {
        checkArgument(StringUtils.isNotBlank(title), ErrorCode.INVALID_TITLE_NULL);
        checkArgument(title.length() <= 50, ErrorCode.INVALID_TITLE_LENGTH);
    }

    public String titleToSlug() {
        return title.toLowerCase()
            .replaceAll(NONLATIN_REGEX, BLANK)
            .replaceAll(WHITESPACE_REGEX, HYPHEN);
    }

    @JsonValue
    public String title() {
        return title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Title title1 = (Title) o;
        return Objects.equals(title, title1.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

}
