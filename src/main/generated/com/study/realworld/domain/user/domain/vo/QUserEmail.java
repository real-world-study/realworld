package com.study.realworld.domain.user.domain.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserEmail is a Querydsl query type for UserEmail
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QUserEmail extends BeanPath<UserEmail> {

    private static final long serialVersionUID = 955939873L;

    public static final QUserEmail userEmail1 = new QUserEmail("userEmail1");

    public final StringPath userEmail = createString("userEmail");

    public QUserEmail(String variable) {
        super(UserEmail.class, forVariable(variable));
    }

    public QUserEmail(Path<? extends UserEmail> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserEmail(PathMetadata metadata) {
        super(UserEmail.class, metadata);
    }

}

