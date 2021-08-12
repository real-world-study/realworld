package com.study.realworld.user.service;

import com.study.realworld.user.domain.User;
import com.study.realworld.user.domain.UserRepository;
import com.study.realworld.user.dto.UserJoinRequest;
import com.study.realworld.user.dto.UserLoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Transactional
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(id + " not found"));
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException(email + " not found"));
    }

    public User save(UserJoinRequest request) {
        User user = UserJoinRequest.toUser(request);
        user.encodePassword(passwordEncoder);

        return userRepository.save(user);
    }

    public User deleteById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(id + " not found"));

        userRepository.delete(user);
        return user;
    }

    @Transactional(readOnly = true)
    public User login(UserLoginRequest request) {
        User user = this.findByEmail(request.getEmail());

        if(!user.matchesPassword(request.getPassword(), passwordEncoder)) {
            throw new NoSuchElementException(request.getPassword() + " wrong wrong wrong triple wrong" + user.getPassword());
        }

        return user;
    }

}
