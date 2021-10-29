package com.study.realworld.article.comment.controller;

import com.study.realworld.article.comment.controller.request.CommentCreateRequest;
import com.study.realworld.article.comment.controller.response.CommentResponse;
import com.study.realworld.article.comment.domain.Comment;
import com.study.realworld.article.comment.service.CommentService;
import com.study.realworld.article.domain.Slug;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PostMapping("/articles/{slug}/comments")
    public ResponseEntity<CommentResponse> createComment(@PathVariable String slug,
        @RequestBody CommentCreateRequest request,
        @AuthenticationPrincipal Long loginId) {
        Comment comment = commentService.createComment(loginId, Slug.of(slug), request.toCommentBody());
        return ResponseEntity.ok().body(CommentResponse.fromComment(comment));
    }

}
