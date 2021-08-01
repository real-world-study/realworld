package com.tistory.povia.realworld.user.repository;

import com.tistory.povia.realworld.user.domain.User;

import java.util.Optional;

public interface UserRepository {
  Optional<User> findById(Long id);
  User save(User user);
}
