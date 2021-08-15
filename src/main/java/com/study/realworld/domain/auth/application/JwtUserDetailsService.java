package com.study.realworld.domain.auth.application;

import com.study.realworld.domain.auth.model.SecurityCustomUser;
import com.study.realworld.domain.user.domain.Email;
import com.study.realworld.domain.user.domain.User;
import com.study.realworld.domain.user.domain.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JwtUserDetailsService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final User user = findUserByEmail(email);
        return SecurityCustomUser.ofUser(user);
    }

    private User findUserByEmail(final String email) {
        return userRepository.findByEmail(new Email(email))
                .orElseThrow(IllegalArgumentException::new);
    }

}
