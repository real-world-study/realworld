package com.study.realworld.domain.article.domain.persist;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.study.realworld.domain.article.domain.vo.ArticleSlug;
import com.study.realworld.domain.favorite.domain.QFavorite;
import com.study.realworld.domain.user.domain.persist.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.querydsl.core.types.ExpressionUtils.count;
import static com.querydsl.core.types.ExpressionUtils.isNotNull;
import static com.querydsl.jpa.JPAExpressions.selectOne;
import static com.study.realworld.domain.article.domain.persist.QArticle.article;
import static com.study.realworld.domain.article.domain.persist.QArticleTag.articleTag;
import static com.study.realworld.domain.favorite.domain.QFavorite.favorite;
import static com.study.realworld.domain.follow.domain.QFollow.follow;
import static com.study.realworld.domain.tag.domain.persist.QTag.tag;
import static com.study.realworld.domain.user.domain.persist.QUser.user;

@RequiredArgsConstructor
@Repository
public class ArticleQueryRepository {

    private final JPAQueryFactory query;

    public Optional<Article> findByArticleSlug(final ArticleSlug articleSlug) {
        return Optional.ofNullable(query
                .selectFrom(article)
                .where(article.articleSlug.eq(articleSlug))
                .fetchOne());
    }

    /**
     * SELECT
     * ARTICLE.*,
     * TAG_NAME,
     * CASE WHEN EXISTS(SELECT * FROM FAVORITE WHERE FAVORITE.USER_ID = 3)
     * THEN 'TRUE'
     * ELSE 'FALSE'
     * END as favorited,
     * (SELECT COUNT(FAVORITE.id) FROM FAVORITE) as favoritesCount,
     * USER.*,
     * CASE WHEN EXISTS(SELECT * FROM FOLLOW WHERE FOLLOW.FOLLOWEE = ARTICLE.AUTHOR AND FOLLOW.FOLLOWER = 3)
     * THEN 'TRUE'
     * ELSE 'FALSE'
     * END as following
     * FROM ARTICLE
     * LEFT JOIN FAVORITE ON FAVORITE.ARTICLE_ID = ARTICLE.ID
     * LEFT JOIN ARTICLE_TAG ON ARTICLE_TAG.ARTICLE_ID =  ARTICLE.ID
     * LEFT JOIN TAG ON TAG.ID = TAG_ID
     * LEFT JOIN USER ON USER.id = ARTICLE.AUTHOR
     * WHERE
     * TAG_NAME = 'angularjs' AND
     * AUTHOR = '2' AND
     * FAVORITE.USER_ID = 3
     */
    public String findArticles(final User loginUser, final Pageable pageable, final String requestTag, final String author, final String favorited) {
        final QFavorite subFavorite = new QFavorite("subFavorite");
        final QFavorite countFavorite = new QFavorite("countFavorite");

        final List<Tuple> tuples = query.select(
                article,
                tag.tagName,
                isNotNull(selectOne().from(favorite).where(favorite.user.eq(loginUser))),
                count(favorite.favoriteId),
                isNotNull(selectOne().from(follow).where(follow.followee.eq(article.author), follow.follower.eq(loginUser)))
        )
                .from(article)
                .leftJoin(favorite).on(favorite.article.eq(article)).fetchJoin()
                .leftJoin(articleTag).on(articleTag.article.eq(article)).fetchJoin()
                .leftJoin(tag).on(articleTag.tag.eq(tag)).fetchJoin()
                .leftJoin(user).on(article.author.eq(user))
                .where(tagEqualTo(requestTag), authEqualTo(author), favoritedEqualTo(favorited))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return "";
    }

    public String findArticles(final Pageable pageable, final String tag, final String author, final String favorited) {
        return null;
    }

    private Predicate favoritedEqualTo(final String favorited) {
        if (favorited.isBlank()) {
            return null;
        }
        return favorite.user.userName.userName.eq(favorited);
    }

    private Predicate tagEqualTo(final String tagString) {
        if (tagString.isBlank()) {
            return null;
        }
        return articleTag.tag.tagName.tagName.eq(tagString);
    }

    private Predicate authEqualTo(final String author) {
        if (author.isBlank()) {
            return null;
        }
        return article.author.userName.userName.eq(author);
    }
}
