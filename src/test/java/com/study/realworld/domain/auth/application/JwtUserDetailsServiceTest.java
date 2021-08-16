package com.study.realworld.domain.auth.application;

import com.study.realworld.domain.user.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import static com.study.realworld.domain.user.domain.BioTest.BIO;
import static com.study.realworld.domain.user.domain.EmailTest.EMAIL;
import static com.study.realworld.domain.user.domain.ImageTest.IMAGE;
import static com.study.realworld.domain.user.domain.NameTest.USERNAME;
import static com.study.realworld.domain.user.domain.PasswordTest.PASSWORD;
import static com.study.realworld.domain.user.domain.UserTest.userBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class JwtUserDetailsServiceTest {

    @Mock private UserRepository userRepository;
    @InjectMocks private JwtUserDetailsService jwtUserDetailsService;

    @DisplayName("JwtUserDetailsService 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final UserDetailsService jwtUserDetailsService = new JwtUserDetailsService(userRepository);

        assertAll(
                () -> assertThat(jwtUserDetailsService).isNotNull(),
                () -> assertThat(jwtUserDetailsService).isInstanceOf(UserDetailsService.class)
        );
    }

    @DisplayName("JwtUserDetailsService 인스턴스 loadUserByUsername 테스트")
    @Test
    void loadUserByUsername_test() {
        final SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");
        final Set<SimpleGrantedAuthority> authorities = Collections.singleton(authority);
        final com.study.realworld.domain.user.domain.User user = userBuilder(new Email(EMAIL), new Name(USERNAME), new Password(PASSWORD), new Bio(BIO), new Image(IMAGE));
        given(userRepository.findByEmail(any())).willReturn(Optional.ofNullable(user));

        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(EMAIL);

        assertAll(
                () -> assertThat(userDetails.getAuthorities()).isEqualTo(authorities),
                () -> assertThat(userDetails.getUsername()).isEqualTo(EMAIL),
                () -> assertThat(userDetails.getPassword()).isEqualTo(PASSWORD)
        );
    }

    public static UserDetails mapToSecurityUser(final User user) {
        final SimpleGrantedAuthority authority = new SimpleGrantedAuthority(User.DEFAULT_AUTHORITY);
        return generateSecurityUser(user.email(), user.password(), authority);
    }

    public static org.springframework.security.core.userdetails.User generateSecurityUser(final Email email, final Password password, final SimpleGrantedAuthority authority) {
        return new org.springframework.security.core.userdetails.User(
                email.email(),
                password.password(),
                Collections.singleton(authority)
        );
    }

}