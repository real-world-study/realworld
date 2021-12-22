package com.study.realworld.domain.article.domain.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QArticleSlug is a Querydsl query type for ArticleSlug
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QArticleSlug extends BeanPath<ArticleSlug> {

    private static final long serialVersionUID = -1186348352L;

    public static final QArticleSlug articleSlug1 = new QArticleSlug("articleSlug1");

    public final StringPath articleSlug = createString("articleSlug");

    public QArticleSlug(String variable) {
        super(ArticleSlug.class, forVariable(variable));
    }

    public QArticleSlug(Path<? extends ArticleSlug> path) {
        super(path.getType(), path.getMetadata());
    }

    public QArticleSlug(PathMetadata metadata) {
        super(ArticleSlug.class, metadata);
    }

}

