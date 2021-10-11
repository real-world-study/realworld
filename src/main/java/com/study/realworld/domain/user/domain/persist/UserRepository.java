package com.study.realworld.domain.user.domain.persist;

import com.study.realworld.domain.user.domain.vo.Email;
import com.study.realworld.domain.user.domain.vo.Name;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(final Email email);
    boolean existsByEmail(final Email email);

    Optional<User> findByUsername(final Name followingName, final Name followerName);

}

