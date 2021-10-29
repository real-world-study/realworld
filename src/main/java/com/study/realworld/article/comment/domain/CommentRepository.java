package com.study.realworld.article.comment.domain;

import com.study.realworld.article.domain.Article;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllByArticle(Article article);

}
