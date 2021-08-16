package com.study.realworld.follow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.study.realworld.follow.entity.Follow;

public interface FollowRepository extends JpaRepository<Follow, Long> {
}
