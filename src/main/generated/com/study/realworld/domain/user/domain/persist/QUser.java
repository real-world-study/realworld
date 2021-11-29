package com.study.realworld.domain.user.domain.persist;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 1431247466L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final com.study.realworld.global.common.QBaseTimeEntity _super = new com.study.realworld.global.common.QBaseTimeEntity(this);

    public final BooleanPath activated = createBoolean("activated");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> deletedAt = _super.deletedAt;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public final com.study.realworld.domain.user.domain.vo.QUserBio userBio;

    public final com.study.realworld.domain.user.domain.vo.QUserEmail userEmail;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final com.study.realworld.domain.user.domain.vo.QUserImage userImage;

    public final com.study.realworld.domain.user.domain.vo.QUserName userName;

    public final com.study.realworld.domain.user.domain.vo.QUserPassword userPassword;

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.userBio = inits.isInitialized("userBio") ? new com.study.realworld.domain.user.domain.vo.QUserBio(forProperty("userBio")) : null;
        this.userEmail = inits.isInitialized("userEmail") ? new com.study.realworld.domain.user.domain.vo.QUserEmail(forProperty("userEmail")) : null;
        this.userImage = inits.isInitialized("userImage") ? new com.study.realworld.domain.user.domain.vo.QUserImage(forProperty("userImage")) : null;
        this.userName = inits.isInitialized("userName") ? new com.study.realworld.domain.user.domain.vo.QUserName(forProperty("userName")) : null;
        this.userPassword = inits.isInitialized("userPassword") ? new com.study.realworld.domain.user.domain.vo.QUserPassword(forProperty("userPassword")) : null;
    }

}

