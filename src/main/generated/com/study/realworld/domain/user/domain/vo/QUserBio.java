package com.study.realworld.domain.user.domain.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserBio is a Querydsl query type for UserBio
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QUserBio extends BeanPath<UserBio> {

    private static final long serialVersionUID = 707136205L;

    public static final QUserBio userBio1 = new QUserBio("userBio1");

    public final StringPath userBio = createString("userBio");

    public QUserBio(String variable) {
        super(UserBio.class, forVariable(variable));
    }

    public QUserBio(Path<? extends UserBio> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserBio(PathMetadata metadata) {
        super(UserBio.class, metadata);
    }

}

