package com.tistory.povia.realworld.auth.service;

import com.tistory.povia.realworld.user.domain.Email;
import com.tistory.povia.realworld.user.domain.Role;
import com.tistory.povia.realworld.user.domain.User;
import com.tistory.povia.realworld.user.service.UserService;
import java.util.Collections;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user =
                userService
                        .findByEmail(new Email(email))
                        .orElseThrow(IllegalArgumentException::new);
        return new org.springframework.security.core.userdetails.User(user.email().address(), user.password(), Collections.singleton(new SimpleGrantedAuthority(Role.USER.value()))) {
        };
    }

}
