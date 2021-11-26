package com.study.realworld.domain.article.domain.persist;

import com.study.realworld.domain.article.domain.vo.ArticleSlug;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    Optional<Article> findByArticleSlug(final ArticleSlug articleSlug);

}
