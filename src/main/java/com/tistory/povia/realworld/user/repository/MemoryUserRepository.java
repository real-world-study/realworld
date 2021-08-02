package com.tistory.povia.realworld.user.repository;

import com.tistory.povia.realworld.user.domain.Email;
import com.tistory.povia.realworld.user.domain.User;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryUserRepository implements UserRepository {

    private final ConcurrentHashMap<Long, User> store;
    private AtomicLong sequence = new AtomicLong(0L);

    public MemoryUserRepository() {
        this.store = new ConcurrentHashMap<>();
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        Optional<User> findUser =
                store.values().stream().filter(user -> user.email().equals(email)).findFirst();

        return findUser;
    }

    @Override
    public User save(User user) {
        user.initId(sequence.addAndGet(1));
        store.put(user.id(), user);
        return user;
    }
}
