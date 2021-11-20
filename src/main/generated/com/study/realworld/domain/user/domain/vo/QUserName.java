package com.study.realworld.domain.user.domain.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserName is a Querydsl query type for UserName
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QUserName extends BeanPath<UserName> {

    private static final long serialVersionUID = 446735718L;

    public static final QUserName userName1 = new QUserName("userName1");

    public final StringPath userName = createString("userName");

    public QUserName(String variable) {
        super(UserName.class, forVariable(variable));
    }

    public QUserName(Path<? extends UserName> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserName(PathMetadata metadata) {
        super(UserName.class, metadata);
    }

}

