package com.study.realworld.domain.article.util;

import com.study.realworld.domain.article.domain.persist.Article;
import com.study.realworld.domain.article.domain.persist.ArticleTag;
import com.study.realworld.domain.tag.domain.persist.Tag;

public class ArticleTagFixture {
    public static ArticleTag createArticleTag(final Article article, final Tag tag) {
        return ArticleTag.builder()
                .article(article)
                .tag(tag)
                .build();
    }
}
