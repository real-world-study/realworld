package com.study.realworld.user.application;

import com.study.realworld.core.domain.user.entity.User;
import com.study.realworld.core.domain.user.repository.UserRepository;
import com.study.realworld.user.presentation.model.UserRegisterModel;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    void registerTest() {

        final String userName = "찬스";
        final String email = "chance@chance.com";
        final String password = "chance";
        final LocalDateTime now = LocalDateTime.now();

        UserRegisterModel userRegisterModel = new UserRegisterModel(userName, email, password);
        User user = User.builder().userName(userName).email(email).password(password).createdAt(now).build();

        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        assertEquals(userService.register(userRegisterModel).getUserName(), user.getUserName());
    }
}
