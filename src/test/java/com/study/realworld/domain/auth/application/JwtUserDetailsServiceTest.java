package com.study.realworld.domain.auth.application;

import com.study.realworld.domain.user.domain.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtUserDetailsServiceTest {

    @Mock private UserRepository userRepository;
    @InjectMocks private UserDetailsService jwtUserDetailsService;

    @DisplayName("JwtUserDetailsService 인스턴스 생성자 테스트")
    @Test
    void constructor_test(){
        final UserDetailsService jwtUserDetailsService = new JwtUserDetailsService(userRepository);

        assertAll(
                () -> assertThat(jwtUserDetailsService).isNotNull(),
                () -> assertThat(jwtUserDetailsService).isInstanceOf(UserDetailsService.class)
        );
    }

}