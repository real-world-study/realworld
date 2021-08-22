package com.study.realworld.user.jwt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import com.study.realworld.user.entity.User;
import com.study.realworld.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @Mock
    private UserRepository userRepository;

    @Test
    void loadUserByUsername() {
        String email = "dolphago@test.net";
        User user = User.builder()
                        .email(email)
                        .password("1234")
                        .build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        final UserDetails userDetails = jwtService.loadUserByUsername(email);
        assertAll(
                () -> assertThat(userDetails.getUsername()).isEqualTo(email),
                () -> assertThat(userDetails.getPassword()).isEqualTo("1234")
        );
    }

}
