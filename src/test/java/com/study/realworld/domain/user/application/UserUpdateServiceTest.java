package com.study.realworld.domain.user.application;

import com.study.realworld.domain.user.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.study.realworld.domain.user.domain.BioTest.BIO;
import static com.study.realworld.domain.user.domain.EmailTest.EMAIL;
import static com.study.realworld.domain.user.domain.ImageTest.IMAGE;
import static com.study.realworld.domain.user.domain.NameTest.USERNAME;
import static com.study.realworld.domain.user.domain.PasswordTest.PASSWORD;
import static com.study.realworld.domain.user.domain.UserTest.userBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class UserUpdateServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserUpdateService userUpdateService;

    @DisplayName("UserUpdateService 생성자 테스트")
    @Test
    void constructor_test() {
        final UserUpdateService userUpdateService = new UserUpdateService(userRepository);

        assertAll(
                () -> assertThat(userUpdateService).isNotNull(),
                () -> assertThat(userUpdateService).isExactlyInstanceOf(UserUpdateService.class)
        );
    }

    @DisplayName("UserUpdateService 인스턴스 update() 테스트")
    @Test
    void update_test() {
        final User user = userBuilder(new Email(EMAIL), new Name(USERNAME),
                new Password(PASSWORD), new Bio(BIO), new Image(IMAGE));
        doReturn(Optional.ofNullable(user)).when(userRepository).findByEmail(any());

//        final User updatedUser = userUpdateService.update();
    }

}