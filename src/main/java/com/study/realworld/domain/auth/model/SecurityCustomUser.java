package com.study.realworld.domain.auth.model;

import com.study.realworld.domain.user.domain.Email;
import com.study.realworld.domain.user.domain.Password;
import com.study.realworld.domain.user.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class SecurityCustomUser extends org.springframework.security.core.userdetails.User {

    public static SecurityCustomUser ofUser(final User user) {
        return fromUserInfo(user.email(), user.password());
    }

    private static SecurityCustomUser fromUserInfo(final Email email, final Password password) {
        final SimpleGrantedAuthority authority = new SimpleGrantedAuthority(User.DEFAULT_AUTHORITY);
        return new SecurityCustomUser(email.email(), password.password(), Collections.singleton(authority));
    }

    public SecurityCustomUser(final String username, final String password, final Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public SecurityCustomUser(final String username, final String password, final boolean enabled, final boolean accountNonExpired, final boolean credentialsNonExpired, final boolean accountNonLocked, final Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

}
