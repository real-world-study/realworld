package com.study.realworld.domain.user.application;

import com.study.realworld.domain.user.domain.Email;
import com.study.realworld.domain.user.domain.Password;
import com.study.realworld.domain.user.domain.User;
import com.study.realworld.domain.user.domain.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Transactional(readOnly = true)
@Service
public class UserFindService {

    private final UserRepository userRepository;

    public UserFindService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUserByEmail(final String email) {
        return userRepository.findByEmail(new Email(email))
                .orElseThrow(IllegalArgumentException::new);
    }

}
