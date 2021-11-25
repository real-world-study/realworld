package com.study.realworld.domain.article.strategy;

@FunctionalInterface
public interface SlugStrategy {
    String mapToSlug(final String url);
}
