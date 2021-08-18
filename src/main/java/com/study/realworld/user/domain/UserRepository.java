package com.study.realworld.user.domain;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(Username username);

    Optional<User> findByEmail(Email email);

}
