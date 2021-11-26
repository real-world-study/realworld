package com.study.realworld.domain.article.api.util;

import com.study.realworld.domain.article.domain.vo.ArticleSlug;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

public class ArticleSlugConverter {

    private static final Logger logger = LoggerFactory.getLogger(ArticleSlugConverter.class);

    @Component
    public class StringToArticleSlugConverter implements Converter<String, ArticleSlug> {
        @Override
        public ArticleSlug convert(final String source) {
            logger.info("StringToArticleSlugConverter : " + source);
            return ArticleSlug.from(source);
        }
    }

    @Component
    public class ArticleSlugToStringConverter implements Converter<ArticleSlug, String> {
        @Override
        public String convert(final ArticleSlug source) {
            logger.info("ArticleSlugToStringConverter : " + source.toString());
            return source.articleSlug();
        }
    }
}
