package com.study.realworld.article.domain;

import com.study.realworld.user.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Optional<Article> findByArticleContentSlugTitleSlug(Slug slug);

    Optional<Article> findByAuthorAndArticleContentSlugTitleSlug(User author, Slug slug);

}
