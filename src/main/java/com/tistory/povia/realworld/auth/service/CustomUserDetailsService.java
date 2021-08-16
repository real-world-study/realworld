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

public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user =
                userService
                        .findByEmail(new Email(email))
                        .orElseThrow(IllegalArgumentException::new);
        return mapToSecurityUser(user);
    }

    private UserDetails mapToSecurityUser(final User user) {
        final SimpleGrantedAuthority authority = new SimpleGrantedAuthority(Role.USER.value());
        return generateSecurityUser(user.email(), user.password(), authority);
    }

    private org.springframework.security.core.userdetails.User generateSecurityUser(
            Email email, String password, final SimpleGrantedAuthority authority) {
        return new org.springframework.security.core.userdetails.User(
                email.address(), password, Collections.singleton(authority));
    }
}
