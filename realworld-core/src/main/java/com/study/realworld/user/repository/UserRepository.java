package com.study.realworld.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.study.realworld.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
