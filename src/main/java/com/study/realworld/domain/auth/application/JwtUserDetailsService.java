package com.study.realworld.domain.auth.application;

import com.study.realworld.domain.user.domain.Email;
import com.study.realworld.domain.user.domain.Password;
import com.study.realworld.domain.user.domain.User;
import com.study.realworld.domain.user.domain.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;

public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public JwtUserDetailsService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final User user = findUserByEmail(email);
        return mapToSecurityUser(user);
    }

    private User findUserByEmail(final String email) {
        return userRepository.findByEmail(new Email(email))
                .orElseThrow(IllegalArgumentException::new);
    }

    private UserDetails mapToSecurityUser(final User user) {
        final SimpleGrantedAuthority authority = new SimpleGrantedAuthority(User.DEFAULT_AUTHORITY);
        return generateSecurityUser(user.email(), user.password(), authority);
    }

    private org.springframework.security.core.userdetails.User generateSecurityUser(final Email email, final Password password, final SimpleGrantedAuthority authority) {
        return new org.springframework.security.core.userdetails.User(
                email.email(),
                password.password(),
                Collections.singleton(authority)
        );
    }

}
