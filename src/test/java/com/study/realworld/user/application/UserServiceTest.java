package com.study.realworld.user.application;

import com.study.realworld.core.domain.user.entity.User;
import com.study.realworld.core.domain.user.repository.UserRepository;
import com.study.realworld.user.application.model.UserLoginModel;
import com.study.realworld.user.application.model.UserRegisterModel;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
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

    @Test
    @DisplayName("현재 로그인된 유저 정보 반환 - 성공")
    void getLoginUserTest() {

        final String email = "chance@chance.com";
        final String password = "chance";
        final Long userId = 1L;

        User user = User.builder().email(email).password(password).build();

        doReturn(Optional.ofNullable(user)).when(userRepository).findById(userId);

        Optional<User> loginUser = userRepository.findById(userId);

        assertEquals(loginUser.get().getEmail(), userService.getLoginUser(userId).getEmail());
    }

    @Test
    @DisplayName("현재 로그인된 유저 정보 반환 - repository에서 찾기 못했을 때")
    void getLoginUserNotExistTest() {

        final Long userId = 1L;

        doReturn(Optional.empty()).when(userRepository).findById(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThatExceptionOfType(NoSuchElementException.class)
            .isThrownBy(() -> userService.getLoginUser(userId));
    }
}
