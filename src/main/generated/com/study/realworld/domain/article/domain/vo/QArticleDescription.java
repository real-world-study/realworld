package com.study.realworld.domain.article.domain.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QArticleDescription is a Querydsl query type for ArticleDescription
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QArticleDescription extends BeanPath<ArticleDescription> {

    private static final long serialVersionUID = -1743300025L;

    public static final QArticleDescription articleDescription1 = new QArticleDescription("articleDescription1");

    public final StringPath articleDescription = createString("articleDescription");

    public QArticleDescription(String variable) {
        super(ArticleDescription.class, forVariable(variable));
    }

    public QArticleDescription(Path<? extends ArticleDescription> path) {
        super(path.getType(), path.getMetadata());
    }

    public QArticleDescription(PathMetadata metadata) {
        super(ArticleDescription.class, metadata);
    }

}

