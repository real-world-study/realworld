package com.study.realworld.domain.follow.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowRepository extends CrudRepository<Follow, Long> {
}
