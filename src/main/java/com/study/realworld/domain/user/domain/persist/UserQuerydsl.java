package com.study.realworld.domain.user.domain.persist;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.realworld.domain.user.domain.vo.UserEmail;
import com.study.realworld.domain.user.domain.vo.UserName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.study.realworld.domain.user.domain.persist.QUser.user;

@RequiredArgsConstructor
@Repository
public class UserQuerydsl {

    private final JPAQueryFactory query;

    public Optional<User> findById(final Long userId) {
        return Optional.ofNullable(query.selectFrom(user).where(user.userId.eq(userId)).fetchOne());
    }

    public Optional<User> findByUserEmail(final UserEmail userEmail) {
        return Optional.ofNullable(query.selectFrom(user).where(user.userEmail.eq(userEmail)).fetchOne());
    }

    public Optional<User> findByUserName(final UserName userName) {
        return Optional.ofNullable(query.selectFrom(user).where(user.userName.eq(userName)).fetchOne());
    }
}
