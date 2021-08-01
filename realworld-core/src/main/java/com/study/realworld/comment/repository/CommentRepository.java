package com.study.realworld.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.study.realworld.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
