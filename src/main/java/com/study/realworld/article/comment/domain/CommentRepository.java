package com.study.realworld.article.comment.domain;

import com.study.realworld.article.domain.Article;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByArticle(Article article);

    Optional<Comment> findByIdAndArticle(Long id, Article article);

}
