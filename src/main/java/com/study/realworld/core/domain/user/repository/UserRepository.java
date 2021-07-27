package com.study.realworld.core.domain.user.repository;

import com.study.realworld.core.domain.user.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Jeongjoon Seo
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}
