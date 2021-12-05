package com.study.realworld.domain.tag.domain.persist;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.study.realworld.domain.tag.domain.QTag.tag;


@RequiredArgsConstructor
@Repository
public class TagQuerydsl {

    private final JPAQueryFactory query;

    public List<Tag> findAll() {
        return query.selectFrom(tag).fetch();
    }
}
