package com.study.realworld.domain.tag.domain.persist;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.realworld.domain.tag.domain.vo.TagName;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.study.realworld.domain.tag.domain.persist.QTag.tag;
import static java.util.Objects.nonNull;


@RequiredArgsConstructor
@Repository
public class TagQuerydsl {

    private final JPAQueryFactory query;

    public List<Tag> findAll() {
        return query.selectFrom(tag).fetch();
    }

    public boolean existsByTagName(final TagName tagName) {
        return nonNull(query.selectFrom(tag)
                .where(tag.tagName.eq(tagName))
                .fetchOne());
    }
}
