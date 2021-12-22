package com.study.realworld.domain.user.domain.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserPassword is a Querydsl query type for UserPassword
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QUserPassword extends BeanPath<UserPassword> {

    private static final long serialVersionUID = -1067450378L;

    public static final QUserPassword userPassword1 = new QUserPassword("userPassword1");

    public final StringPath userPassword = createString("userPassword");

    public QUserPassword(String variable) {
        super(UserPassword.class, forVariable(variable));
    }

    public QUserPassword(Path<? extends UserPassword> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserPassword(PathMetadata metadata) {
        super(UserPassword.class, metadata);
    }

}

