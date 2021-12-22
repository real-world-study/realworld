package com.study.realworld.domain.article.domain.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QArticleBody is a Querydsl query type for ArticleBody
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QArticleBody extends BeanPath<ArticleBody> {

    private static final long serialVersionUID = -1186852425L;

    public static final QArticleBody articleBody1 = new QArticleBody("articleBody1");

    public final StringPath articleBody = createString("articleBody");

    public QArticleBody(String variable) {
        super(ArticleBody.class, forVariable(variable));
    }

    public QArticleBody(Path<? extends ArticleBody> path) {
        super(path.getType(), path.getMetadata());
    }

    public QArticleBody(PathMetadata metadata) {
        super(ArticleBody.class, metadata);
    }

}

