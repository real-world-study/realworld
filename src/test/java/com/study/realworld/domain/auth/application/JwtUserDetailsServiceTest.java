package com.study.realworld.domain.auth.application;

import com.study.realworld.domain.user.domain.*;
import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static com.study.realworld.domain.user.domain.BioTest.BIO;
import static com.study.realworld.domain.user.domain.EmailTest.EMAIL;
import static com.study.realworld.domain.user.domain.ImageTest.IMAGE;
import static com.study.realworld.domain.user.domain.NameTest.USERNAME;
import static com.study.realworld.domain.user.domain.PasswordTest.PASSWORD;
import static com.study.realworld.domain.user.domain.PasswordTest.PASSWORD_ENCODER;
import static com.study.realworld.domain.user.domain.UserTest.userBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class JwtUserDetailsServiceTest {

    @Mock private UserRepository userRepository;
    @InjectMocks private JwtUserDetailsService jwtUserDetailsService;

    @DisplayName("JwtUserDetailsService 인스턴스 생성자 테스트")
    @Test
    void constructor_test(){
        final UserDetailsService jwtUserDetailsService = new JwtUserDetailsService(userRepository);

        assertAll(
                () -> assertThat(jwtUserDetailsService).isNotNull(),
                () -> assertThat(jwtUserDetailsService).isInstanceOf(UserDetailsService.class)
        );
    }

    @DisplayName("JwtUserDetailsService 인스턴스 loadUserByUsername 테스트")
    @Test
    void loadUserByUsername_test(){
        final String authorityString = "USER";
        final com.study.realworld.domain.user.domain.User user = userBuilder(new Email(EMAIL), new Name(USERNAME), new Password(PASSWORD), new Bio(BIO), new Image(IMAGE)).encode(PASSWORD_ENCODER);
        given(userRepository.findByEmail(any())).willReturn(Optional.ofNullable(user));

        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(EMAIL);

        assertAll(
                () -> assertThat(userDetails.getAuthorities()).isEqualTo(authorityString),
                () -> assertThat(userDetails.getUsername()).isEqualTo(EMAIL),
                () -> assertThat(userDetails.getPassword()).isEqualTo(PASSWORD)
        );
    }

}