package com.study.realworld.article.domain;

import static com.google.common.base.Preconditions.checkArgument;

import com.study.realworld.global.exception.ErrorCode;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Title {

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
        checkArgument(title.length() <= 50, ErrorCode.INVALID_TITLE_LENGTH);
    }

}
