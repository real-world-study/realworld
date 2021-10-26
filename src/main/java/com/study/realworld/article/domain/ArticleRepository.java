package com.study.realworld.article.domain;

import com.study.realworld.user.domain.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    @EntityGraph(attributePaths = {"author"}, type = EntityGraphType.FETCH)
    Optional<Article> findByArticleContentSlugTitleSlug(Slug slug);

    @EntityGraph(attributePaths = {"author"}, type = EntityGraphType.FETCH)
    Optional<Article> findByAuthorAndArticleContentSlugTitleSlug(User author, Slug slug);

    @EntityGraph(attributePaths = {"author"}, type = EntityGraphType.FETCH)
    @Query(
        value = "select distinct a "
            + "from Article a "
            + "join a.author u "
            + "inner join a.articleContent.tags t "
            + "where "
            + "(:author is null or :author = u.profile.username.name) "
            + "and "
            + "(:tag is null or :tag = t.name) "
            + "order by a.createdAt"
    )
    Page<Article> findPageByAuthorAndTag(Pageable pageable, String author, String tag);

}
