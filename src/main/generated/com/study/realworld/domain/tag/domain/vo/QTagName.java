package com.study.realworld.domain.tag.domain.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QTagName is a Querydsl query type for TagName
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QTagName extends BeanPath<TagName> {

    private static final long serialVersionUID = 560676200L;

    public static final QTagName tagName1 = new QTagName("tagName1");

    public final StringPath tagName = createString("tagName");

    public QTagName(String variable) {
        super(TagName.class, forVariable(variable));
    }

    public QTagName(Path<? extends TagName> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTagName(PathMetadata metadata) {
        super(TagName.class, metadata);
    }

}

