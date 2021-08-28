package com.study.realworld.domain.user.application;

import com.study.realworld.domain.user.domain.*;
import com.study.realworld.domain.user.dto.UserUpdateRequest;
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
import static org.mockito.Mockito.doReturn;

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
        final Email email = new Email(EMAIL);
        final Email updateEmail = new Email("updateEmail@email.net");
        final Bio updateBio = new Bio("updateBio");
        final Image updateImage = new Image("updateImage");

        final Name username = new Name(USERNAME);
        final Password password = new Password(PASSWORD);
        final User user = userBuilder(new Email(EMAIL), username, password, new Bio(BIO), new Image(IMAGE));
        doReturn(Optional.ofNullable(user)).when(userRepository).findByEmail(any());

        final UserUpdateRequest userUpdateRequest = new UserUpdateRequest(updateEmail, updateBio, updateImage);
        final User updatedUser = userUpdateService.update(userUpdateRequest.toEntity(), email);

        assertAll(
                () -> assertThat(updatedUser.email()).isEqualTo(updateEmail),
                () -> assertThat(updatedUser.username()).isEqualTo(username),
                () -> assertThat(updatedUser.password()).isEqualTo(password),
                () -> assertThat(updatedUser.bio()).isEqualTo(updateBio),
                () -> assertThat(updatedUser.image()).isEqualTo(updateImage)
        );
    }

}