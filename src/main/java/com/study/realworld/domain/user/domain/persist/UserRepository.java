package com.study.realworld.domain.user.domain.persist;

import com.study.realworld.domain.user.domain.vo.UserEmail;
import com.study.realworld.domain.user.domain.vo.UserName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(final UserName userName);

    Optional<User> findByUserEmail(final UserEmail userEmail);

    boolean existsByUserEmail(final UserEmail userEmail);
}
