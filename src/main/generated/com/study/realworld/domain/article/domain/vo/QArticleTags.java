package com.study.realworld.domain.article.domain.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArticleTags is a Querydsl query type for ArticleTags
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QArticleTags extends BeanPath<ArticleTags> {

    private static final long serialVersionUID = -1186329554L;

    public static final QArticleTags articleTags1 = new QArticleTags("articleTags1");

    public final ListPath<com.study.realworld.domain.article.domain.persist.ArticleTag, com.study.realworld.domain.article.domain.persist.QArticleTag> articleTags = this.<com.study.realworld.domain.article.domain.persist.ArticleTag, com.study.realworld.domain.article.domain.persist.QArticleTag>createList("articleTags", com.study.realworld.domain.article.domain.persist.ArticleTag.class, com.study.realworld.domain.article.domain.persist.QArticleTag.class, PathInits.DIRECT2);

    public QArticleTags(String variable) {
        super(ArticleTags.class, forVariable(variable));
    }

    public QArticleTags(Path<? extends ArticleTags> path) {
        super(path.getType(), path.getMetadata());
    }

    public QArticleTags(PathMetadata metadata) {
        super(ArticleTags.class, metadata);
    }

}

