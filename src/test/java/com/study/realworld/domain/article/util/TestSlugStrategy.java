package com.study.realworld.domain.article.util;

import com.study.realworld.domain.article.strategy.SlugStrategy;

public class TestSlugStrategy implements SlugStrategy {
    private static final String FIRST_REGEX = "\\$,'\"|\\s|\\.|\\?";
    private static final String SECOND_REGEX = "-{2,}";
    private static final String THIRD_REGEX = "(^-)|(-$)";
    private static final String REPLACEMENT = "-";
    private static final String EMPTY = "";

    @Override
    public String mapToSlug(final String url) {
        return url.toLowerCase()
                .replaceAll(FIRST_REGEX, REPLACEMENT)
                .replaceAll(SECOND_REGEX, REPLACEMENT)
                .replaceAll(THIRD_REGEX, EMPTY);
    }
}
