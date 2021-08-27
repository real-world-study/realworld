package com.study.realworld.core.domain.user.repository;

import com.study.realworld.core.domain.user.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Jeongjoon Seo
 */
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
