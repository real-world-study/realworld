package com.study.realworld.user.domain;

import com.study.realworld.user.domain.vo.Email;
import com.study.realworld.user.domain.vo.Username;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByProfileUsername(Username username);

    Optional<User> findByEmail(Email email);

}
