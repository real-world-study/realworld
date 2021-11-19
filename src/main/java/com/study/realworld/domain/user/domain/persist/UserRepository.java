package com.study.realworld.domain.user.domain.persist;

import com.study.realworld.domain.user.domain.vo.UserEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserEmail(final UserEmail userEmail);

    boolean existsByUserEmail(final UserEmail userEmail);
}
