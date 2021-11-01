package com.study.realworld.comment.controller;

import com.study.realworld.article.domain.Slug;
import com.study.realworld.comment.dto.request.CommentCreateRequest;
import com.study.realworld.comment.dto.response.CommentResponse;
import com.study.realworld.comment.dto.response.CommentsResponse;
import com.study.realworld.comment.service.CommentService;
import com.study.realworld.global.security.CurrentUserId;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    public ResponseEntity<CommentsResponse> getCommentByArticleSlug(@PathVariable String slug, @CurrentUserId Long userId) {
        CommentsResponse commentsResponse = Optional.ofNullable(userId)
            .map(id -> commentService.findCommentsByArticleSlug(id, Slug.of(slug)))
            .orElse(commentService.findCommentsByArticleSlug(Slug.of(slug)));
        return ResponseEntity.ok().body(commentsResponse);
    }

    @PostMapping("/articles/{slug}/comments")
    public ResponseEntity<CommentResponse> createComment(@PathVariable String slug,
        @RequestBody CommentCreateRequest request, @CurrentUserId Long loginId) {
        CommentResponse commentResponse = commentService.createComment(loginId, Slug.of(slug), request.toCommentBody());
        return ResponseEntity.ok().body(commentResponse);
    }

    @DeleteMapping("/articles/{slug}/comments/{id}")
    public void deleteComment(@PathVariable String slug, @PathVariable Long id, @CurrentUserId Long loginId) {
        commentService.deleteCommentByCommentId(loginId, Slug.of(slug), id);
    }

}
