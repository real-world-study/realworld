package com.study.realworld.domain.article.domain.persist;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArticleTag is a Querydsl query type for ArticleTag
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QArticleTag extends EntityPathBase<ArticleTag> {

    private static final long serialVersionUID = 585513746L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QArticleTag articleTag = new QArticleTag("articleTag");

    public final QArticle article;

    public final NumberPath<Long> articleTagId = createNumber("articleTagId", Long.class);

    public final com.study.realworld.domain.tag.domain.persist.QTag tag;

    public QArticleTag(String variable) {
        this(ArticleTag.class, forVariable(variable), INITS);
    }

    public QArticleTag(Path<? extends ArticleTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QArticleTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QArticleTag(PathMetadata metadata, PathInits inits) {
        this(ArticleTag.class, metadata, inits);
    }

    public QArticleTag(Class<? extends ArticleTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.article = inits.isInitialized("article") ? new QArticle(forProperty("article"), inits.get("article")) : null;
        this.tag = inits.isInitialized("tag") ? new com.study.realworld.domain.tag.domain.persist.QTag(forProperty("tag"), inits.get("tag")) : null;
    }

}

