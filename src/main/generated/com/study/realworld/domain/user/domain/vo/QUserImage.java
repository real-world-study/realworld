package com.study.realworld.domain.user.domain.vo;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserImage is a Querydsl query type for UserImage
 */
@Generated("com.querydsl.codegen.EmbeddableSerializer")
public class QUserImage extends BeanPath<UserImage> {

    private static final long serialVersionUID = 959633888L;

    public static final QUserImage userImage1 = new QUserImage("userImage1");

    public final StringPath userImage = createString("userImage");

    public QUserImage(String variable) {
        super(UserImage.class, forVariable(variable));
    }

    public QUserImage(Path<? extends UserImage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserImage(PathMetadata metadata) {
        super(UserImage.class, metadata);
    }

}

