package com.study.realworld.domain.article.domain.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QArticleTitle is a Querydsl query type for ArticleTitle
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QArticleTitle extends BeanPath<ArticleTitle> {

    private static final long serialVersionUID = 1878740195L;

    public static final QArticleTitle articleTitle1 = new QArticleTitle("articleTitle1");

    public final StringPath articleTitle = createString("articleTitle");

    public QArticleTitle(String variable) {
        super(ArticleTitle.class, forVariable(variable));
    }

    public QArticleTitle(Path<? extends ArticleTitle> path) {
        super(path.getType(), path.getMetadata());
    }

    public QArticleTitle(PathMetadata metadata) {
        super(ArticleTitle.class, metadata);
    }

}

