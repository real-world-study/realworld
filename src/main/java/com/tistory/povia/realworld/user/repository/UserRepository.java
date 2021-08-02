package com.tistory.povia.realworld.user.repository;

import com.tistory.povia.realworld.user.domain.Email;
import com.tistory.povia.realworld.user.domain.User;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(Email email);

    User save(User user);
}
