package com.study.realworld.comment.service;

import com.study.realworld.article.domain.Article;
import com.study.realworld.article.domain.Slug;
import com.study.realworld.article.service.ArticleService;
import com.study.realworld.comment.domain.Comment;
import com.study.realworld.comment.domain.CommentBody;
import com.study.realworld.comment.domain.CommentRepository;
import com.study.realworld.comment.dto.response.CommentResponse;
import com.study.realworld.comment.dto.response.CommentsResponse;
import com.study.realworld.global.exception.BusinessException;
import com.study.realworld.global.exception.ErrorCode;
import com.study.realworld.user.domain.User;
import com.study.realworld.user.service.UserService;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;
    private final ArticleService articleService;

    public CommentService(CommentRepository commentRepository, UserService userService, ArticleService articleService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.articleService = articleService;
    }

    @Transactional(readOnly = true)
    public CommentsResponse findCommentsByArticleSlug(Slug slug) {
        Article article = articleService.findBySlug(slug);

        List<Comment> comments = commentRepository.findAllByArticle(article);
        return CommentsResponse.fromComments(comments);
    }

    @Transactional(readOnly = true)
    public CommentsResponse findCommentsByArticleSlug(Long userId, Slug slug) {
        User user = userService.findById(userId);
        Article article = articleService.findBySlug(slug);

        List<Comment> comments = commentRepository.findAllByArticle(article);
        return CommentsResponse.fromCommentsAndUser(comments, user);
    }

    @Transactional
    public CommentResponse createComment(Long userId, Slug slug, CommentBody commentBody) {
        User author = userService.findById(userId);
        Article article = articleService.findBySlug(slug);

        Comment comment = Comment.from(commentBody, author, article);
        return CommentResponse.fromComment(commentRepository.save(comment));
    }

    @Transactional
    public void deleteCommentByCommentId(Long userId, Slug slug, Long commentId) {
        User author = userService.findById(userId);
        Article article = articleService.findBySlug(slug);

        Comment comment = findByIdAndArticle(commentId, article);
        comment.deleteCommentByAuthor(author);
    }

    private Comment findByIdAndArticle(Long id, Article article) {
        return commentRepository.findByIdAndArticle(id, article)
            .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_COMMENT_NOT_FOUND));
    }

}
