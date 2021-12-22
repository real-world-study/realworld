package com.study.realworld.domain.follow.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFollow is a Querydsl query type for Follow
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QFollow extends EntityPathBase<Follow> {

    private static final long serialVersionUID = -1216297956L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFollow follow = new QFollow("follow");

    public final com.study.realworld.domain.user.domain.persist.QUser followee;

    public final com.study.realworld.domain.user.domain.persist.QUser follower;

    public final NumberPath<Long> followId = createNumber("followId", Long.class);

    public QFollow(String variable) {
        this(Follow.class, forVariable(variable), INITS);
    }

    public QFollow(Path<? extends Follow> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFollow(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFollow(PathMetadata metadata, PathInits inits) {
        this(Follow.class, metadata, inits);
    }

    public QFollow(Class<? extends Follow> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.followee = inits.isInitialized("followee") ? new com.study.realworld.domain.user.domain.persist.QUser(forProperty("followee"), inits.get("followee")) : null;
        this.follower = inits.isInitialized("follower") ? new com.study.realworld.domain.user.domain.persist.QUser(forProperty("follower"), inits.get("follower")) : null;
    }

}

