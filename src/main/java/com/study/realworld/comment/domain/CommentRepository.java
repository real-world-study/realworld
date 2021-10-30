package com.study.realworld.comment.domain;

import com.study.realworld.article.domain.Article;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    @EntityGraph(attributePaths = {"author"}, type = EntityGraphType.FETCH)
    List<Comment> findAllByArticle(Article article);

    @EntityGraph(attributePaths = {"author"}, type = EntityGraphType.FETCH)
    Optional<Comment> findByIdAndArticle(Long id, Article article);

}
