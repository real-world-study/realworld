package com.study.realworld.domain.tag.domain.persist;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTag is a Querydsl query type for Tag
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QTag extends EntityPathBase<Tag> {

    private static final long serialVersionUID = -1210076952L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTag tag = new QTag("tag");

    public final NumberPath<Long> tagId = createNumber("tagId", Long.class);

    public final com.study.realworld.domain.tag.domain.vo.QTagName tagName;

    public QTag(String variable) {
        this(Tag.class, forVariable(variable), INITS);
    }

    public QTag(Path<? extends Tag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTag(PathMetadata metadata, PathInits inits) {
        this(Tag.class, metadata, inits);
    }

    public QTag(Class<? extends Tag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.tagName = inits.isInitialized("tagName") ? new com.study.realworld.domain.tag.domain.vo.QTagName(forProperty("tagName")) : null;
    }

}

