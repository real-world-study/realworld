package com.study.realworld.domain.article.domain.persist;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QArticle is a Querydsl query type for Article
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QArticle extends EntityPathBase<Article> {

    private static final long serialVersionUID = 1072788328L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QArticle article = new QArticle("article");

    public final com.study.realworld.global.common.QBaseTimeEntity _super = new com.study.realworld.global.common.QBaseTimeEntity(this);

    public final BooleanPath activated = createBoolean("activated");

    public final com.study.realworld.domain.article.domain.vo.QArticleBody articleBody;

    public final com.study.realworld.domain.article.domain.vo.QArticleDescription articleDescription;

    public final NumberPath<Long> articleId = createNumber("articleId", Long.class);

    public final com.study.realworld.domain.article.domain.vo.QArticleSlug articleSlug;

    public final com.study.realworld.domain.article.domain.vo.QArticleTitle articleTitle;

    public final com.study.realworld.domain.user.domain.persist.QUser author;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QArticle(String variable) {
        this(Article.class, forVariable(variable), INITS);
    }

    public QArticle(Path<? extends Article> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QArticle(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QArticle(PathMetadata metadata, PathInits inits) {
        this(Article.class, metadata, inits);
    }

    public QArticle(Class<? extends Article> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.articleBody = inits.isInitialized("articleBody") ? new com.study.realworld.domain.article.domain.vo.QArticleBody(forProperty("articleBody")) : null;
        this.articleDescription = inits.isInitialized("articleDescription") ? new com.study.realworld.domain.article.domain.vo.QArticleDescription(forProperty("articleDescription")) : null;
        this.articleSlug = inits.isInitialized("articleSlug") ? new com.study.realworld.domain.article.domain.vo.QArticleSlug(forProperty("articleSlug")) : null;
        this.articleTitle = inits.isInitialized("articleTitle") ? new com.study.realworld.domain.article.domain.vo.QArticleTitle(forProperty("articleTitle")) : null;
        this.author = inits.isInitialized("author") ? new com.study.realworld.domain.user.domain.persist.QUser(forProperty("author"), inits.get("author")) : null;
    }

}

