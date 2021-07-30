package com.tistory.povia.realworld.user.repository;

import com.tistory.povia.realworld.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findById(Long id);
}
