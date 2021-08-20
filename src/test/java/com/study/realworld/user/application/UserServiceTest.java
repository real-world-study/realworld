package com.study.realworld.user.application;

import com.study.realworld.core.domain.user.entity.User;
import com.study.realworld.core.domain.user.repository.UserRepository;
import com.study.realworld.user.application.model.UserLoginModel;
import com.study.realworld.user.application.model.UserRegisterModel;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

/**
 * @author Jeongjoon Seo
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Test
    void registerTest() {

        final String username = "찬스";
        final String email = "chance@chance.com";
        final String password = "chance";
        final LocalDateTime now = LocalDateTime.now();

        UserRegisterModel userRegisterModel = new UserRegisterModel(username, email, password);
        User user = User.builder().username(username).email(email).password(password).createdAt(now).build();

        doReturn(user).when(userRepository).save(Mockito.any(User.class));

        assertEquals(user.getUsername(), userService.register(userRegisterModel).getUsername());
    }

    @Test
    void loginTest() {

        final String email = "chance@chance.com";
        final String password = "chance";

        UserLoginModel userLoginModel = new UserLoginModel(email, password);
        User user = User.builder().email(email).password(password).build();

        doReturn(Optional.of(user)).when(userRepository).findByEmail(email);
        when(passwordEncoder.matches(any(), any())).thenReturn(true);

        assertEquals(user.getEmail(), userService.login(userLoginModel).getEmail());
    }

    @Test
    void loginFailByPasswordTest() {

        final String email = "chance@chance.com";
        final String password = "chance";

        UserLoginModel userLoginModel = new UserLoginModel(email, password);
        User user = User.builder().email(email).password(password).build();

        doReturn(Optional.of(user)).when(userRepository).findByEmail(email);
        when(passwordEncoder.matches(any(), any())).thenReturn(false);

        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> userService.login(userLoginModel))
            .withMessage("invalid password");
    }
}
