package com.study.realworld.article.comment.controller;

import com.study.realworld.article.comment.controller.request.CommentCreateRequest;
import com.study.realworld.article.comment.controller.response.CommentResponse;
import com.study.realworld.article.comment.controller.response.CommentsResponse;
import com.study.realworld.article.comment.domain.Comment;
import com.study.realworld.article.comment.service.CommentService;
import com.study.realworld.article.domain.Slug;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/articles/{slug}/comments")
    public ResponseEntity<CommentsResponse> getCommentByArticleSlug(@PathVariable String slug) {
        List<Comment> comments = commentService.getCommentsByArticleSlug(Slug.of(slug));
        return ResponseEntity.ok().body(CommentsResponse.fromComments(comments));
    }

    @PostMapping("/articles/{slug}/comments")
    public ResponseEntity<CommentResponse> createComment(@PathVariable String slug,
        @RequestBody CommentCreateRequest request,
        @AuthenticationPrincipal Long loginId) {
        Comment comment = commentService.createComment(loginId, Slug.of(slug), request.toCommentBody());
        return ResponseEntity.ok().body(CommentResponse.fromComment(comment));
    }

}
