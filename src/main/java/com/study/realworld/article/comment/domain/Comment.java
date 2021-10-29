package com.study.realworld.article.comment.domain;

import com.study.realworld.article.domain.Article;
import com.study.realworld.global.domain.BaseTimeEntity;
import com.study.realworld.user.domain.User;
import java.util.Objects;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private CommentBody commentBody;

    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_comment_to_user_id"))
    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

    @JoinColumn(name = "article_id", nullable = false, foreignKey = @ForeignKey(name = "fk_comment_to_article_id"))
    @ManyToOne(fetch = FetchType.LAZY)
    private Article article;

    protected Comment() {
    }

    private Comment(CommentBody commentBody, User author, Article article) {
        this.commentBody = commentBody;
        this.author = author;
        this.article = article;
    }

    public static Comment from(CommentBody commentBody, User author, Article article) {
        return new Comment(commentBody, author, article);
    }

    public Long id() {
        return id;
    }

    public CommentBody commentBody() {
        return commentBody;
    }

    public User author() {
        return author;
    }

    public Article article() {
        return article;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comment comment = (Comment) o;
        return Objects.equals(commentBody, comment.commentBody) && Objects
            .equals(author, comment.author) && Objects.equals(article, comment.article);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentBody, author, article);
    }

}
