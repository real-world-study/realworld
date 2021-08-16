package com.study.realworld.article.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.study.realworld.article.entity.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
}
