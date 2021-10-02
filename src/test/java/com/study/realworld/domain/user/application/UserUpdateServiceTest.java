package com.study.realworld.domain.user.application;

import com.study.realworld.domain.user.domain.*;
import com.study.realworld.domain.user.dto.UserUpdateRequest;
import com.study.realworld.domain.user.error.exception.DuplicatedEmailException;
import com.study.realworld.domain.user.error.exception.IdentityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static com.study.realworld.domain.user.domain.BioTest.BIO;
import static com.study.realworld.domain.user.domain.EmailTest.EMAIL;
import static com.study.realworld.domain.user.domain.ImageTest.IMAGE;
import static com.study.realworld.domain.user.domain.NameTest.USERNAME;
import static com.study.realworld.domain.user.domain.PasswordTest.PASSWORD;
import static com.study.realworld.domain.user.domain.UserTest.userBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
        final Long id = 1L;
        final Email updateEmail = new Email("updateEmail@email.net");
        final Bio updateBio = new Bio("updateBio");
        final Image updateImage = new Image("updateImage");

        final Name username = new Name(USERNAME);
        final Password password = new Password(PASSWORD);
        final User user = userBuilder(new Email(EMAIL), username, password, new Bio(BIO), new Image(IMAGE));

        ReflectionTestUtils.setField(user, "id", id);
        doReturn(Optional.ofNullable(user)).when(userRepository).findById(any());
        doReturn(false).when(userRepository).existsByEmail(any());

        final UserUpdateRequest userUpdateRequest = new UserUpdateRequest(updateEmail, updateBio, updateImage);
        final User updatedUser = userUpdateService.update(userUpdateRequest.toEntity(), id);

        assertAll(
                () -> assertThat(updatedUser.email()).isEqualTo(updateEmail),
                () -> assertThat(updatedUser.username()).isEqualTo(username),
                () -> assertThat(updatedUser.password()).isEqualTo(password),
                () -> assertThat(updatedUser.bio()).isEqualTo(updateBio),
                () -> assertThat(updatedUser.image()).isEqualTo(updateImage)
        );
    }

    @DisplayName("UserUpdateService 인스턴스 존재하지 않는 email 로 findByEmail() 실패 테스트")
    @Test
    void fail_findByEmail_test() {
        final Long id = 1L;
        final Email updateEmail = new Email("updateEmail");
        final Bio updateBio = new Bio("updateBio");
        final Image updateImage = new Image("updateImage");
        doReturn(Optional.ofNullable(null)).when(userRepository).findById(any());

        final UserUpdateRequest userUpdateRequest = new UserUpdateRequest(updateEmail, updateBio, updateImage);
        assertThatThrownBy(() -> userUpdateService.update(userUpdateRequest.toEntity(), id))
                .isInstanceOf(IdentityNotFoundException.class)
                .hasMessage(String.format("식별자 : [ %s ] 를 찾을 수 없습니다.", id));
    }

    @DisplayName("UserUpdateService 인스턴스 email 중복으로 인한 update() 실패 테스트")
    @Test
    void fail_update_test() {
        final Long id = 1L;
        final Email email = new Email(EMAIL);
        final Bio updateBio = new Bio("updateBio");
        final Image updateImage = new Image("updateImage");

        final Name username = new Name(USERNAME);
        final Password password = new Password(PASSWORD);
        final User user = userBuilder(email, username, password, new Bio(BIO), new Image(IMAGE));
        ReflectionTestUtils.setField(user, "id", id);
        doReturn(true).when(userRepository).existsByEmail(any());
        doReturn(Optional.ofNullable(user)).when(userRepository).findById(any());

        final UserUpdateRequest userUpdateRequest = new UserUpdateRequest(email, updateBio, updateImage);
        assertThatThrownBy(() -> userUpdateService.update(userUpdateRequest.toEntity(), id))
                .isInstanceOf(DuplicatedEmailException.class)
                .hasMessage(String.format("이메일 : [ %s ] 가 이미 존재합니다.", email.email()));
    }

}