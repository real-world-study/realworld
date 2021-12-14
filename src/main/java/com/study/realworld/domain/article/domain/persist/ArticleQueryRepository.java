package com.study.realworld.domain.article.domain.persist;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.realworld.domain.article.domain.vo.ArticleSlug;
import com.study.realworld.domain.article.dto.ArticleInfo;
import com.study.realworld.domain.article.dto.ArticleListInfo;
import com.study.realworld.domain.user.domain.persist.User;
import io.jsonwebtoken.lang.Strings;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.querydsl.core.types.ExpressionUtils.count;
import static com.querydsl.core.types.ExpressionUtils.isNotNull;
import static com.querydsl.core.types.Projections.constructor;
import static com.querydsl.core.types.dsl.Expressions.asBoolean;
import static com.querydsl.jpa.JPAExpressions.selectOne;
import static com.study.realworld.domain.article.domain.persist.QArticle.article;
import static com.study.realworld.domain.article.domain.persist.QArticleTag.articleTag;
import static com.study.realworld.domain.favorite.domain.QFavorite.favorite;
import static com.study.realworld.domain.follow.domain.QFollow.follow;
import static com.study.realworld.domain.user.domain.persist.QUser.user;

@RequiredArgsConstructor
@Repository
public class ArticleQueryRepository {

    private final JPAQueryFactory query;

    public Optional<Article> findArticleByArticleSlug(final ArticleSlug articleSlug) {
        return Optional.ofNullable(query
                .selectFrom(article)
                .where(article.articleSlug.eq(articleSlug))
                .fetchOne());
    }

    public ArticleInfo findByArticleSlug(final User loginUser, final ArticleSlug articleSlug) {
        return query.select(constructor(ArticleInfo.class,
                        article,
                        isNotNull(selectOne().from(favorite).where(favorite.user.eq(loginUser))),
                        count(favorite.favoriteId),
                        isNotNull(selectOne().from(follow).where(follow.followee.eq(article.author), follow.follower.eq(loginUser))))
                )
                .from(article)
                .leftJoin(favorite).on(favorite.article.eq(article)).fetchJoin()
                .leftJoin(user).on(article.author.eq(user)).fetchJoin()
                .where(article.articleSlug.eq(articleSlug))
                .fetchOne();
    }

    public ArticleInfo findByArticleSlug(final ArticleSlug articleSlug) {
        return query.select(constructor(ArticleInfo.class,
                        article,
                        asBoolean(false),
                        count(favorite.favoriteId),
                        asBoolean(false)
                ))
                .from(article)
                .leftJoin(favorite).on(favorite.article.eq(article)).fetchJoin()
                .leftJoin(user).on(article.author.eq(user)).fetchJoin()
                .where(article.articleSlug.eq(articleSlug))
                .fetchOne();
    }

    public List<ArticleListInfo> findArticles(final User loginUser, final Pageable pageable, final String requestTag, final String author, final String favorited) {
        return query.select(constructor(ArticleListInfo.class,
                        article,
                        isNotNull(selectOne().from(favorite).where(favorite.user.eq(loginUser))),
                        count(favorite.favoriteId),
                        isNotNull(selectOne().from(follow).where(follow.followee.eq(article.author), follow.follower.eq(loginUser))))
                )
                .from(article)
                .leftJoin(favorite).on(favorite.article.eq(article)).fetchJoin()
                .leftJoin(user).on(article.author.eq(user))
                .where(tagEqualTo(requestTag), authEqualTo(author), favoritedEqualTo(favorited))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    public List<ArticleListInfo> findArticles(final Pageable pageable, final String tag, final String author, final String favorited) {
        return null;
    }

    private Predicate favoritedEqualTo(final String favorited) {
        if (!Strings.hasText(favorited)) {
            return null;
        }
        return favorite.user.userName.userName.eq(favorited);
    }

    private Predicate tagEqualTo(final String tagString) {
        if (!Strings.hasText(tagString)) {
            return null;
        }
        return articleTag.tag.tagName.tagName.eq(tagString);
    }

    private Predicate authEqualTo(final String author) {
        if (!Strings.hasText(author)) {
            return null;
        }
        return article.author.userName.userName.eq(author);
    }
}
