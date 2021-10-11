package com.study.realworld.domain.auth.application;

import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.domain.user.domain.persist.UserRepository;
import com.study.realworld.domain.user.domain.persist.UserTest;
import com.study.realworld.domain.user.domain.vo.*;
import com.study.realworld.domain.user.error.exception.EmailNotFoundException;
import com.study.realworld.domain.user.error.exception.PasswordMissMatchException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.study.realworld.domain.user.domain.vo.BioTest.BIO;
import static com.study.realworld.domain.user.domain.vo.EmailTest.EMAIL;
import static com.study.realworld.domain.user.domain.vo.ImageTest.IMAGE;
import static com.study.realworld.domain.user.domain.vo.NameTest.USERNAME;
import static com.study.realworld.domain.user.domain.vo.PasswordTest.PASSWORD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class AuthLoginServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @InjectMocks private AuthLoginService authLoginService;

    @DisplayName("AuthLoginService 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final AuthLoginService authLoginService = new AuthLoginService(userRepository, passwordEncoder);

        assertAll(
                () -> assertThat(authLoginService).isNotNull(),
                () -> assertThat(authLoginService).isExactlyInstanceOf(AuthLoginService.class)
        );
    }

    @DisplayName("AuthLoginService 인스턴스 login 테스트")
    @Test
    void login_test() {
        final Email email = new Email(EMAIL);
        final String rawPasswordString = "encode not yet";
        final Password rawPassword = new Password(rawPasswordString);
        final User joinedUser = UserTest.userBuilder(email, new Name(USERNAME), new Password(PASSWORD), new Bio(BIO), new Image(IMAGE));
        given(userRepository.findByEmail(any())).willReturn(Optional.ofNullable(joinedUser));
        given(passwordEncoder.matches(rawPasswordString, PASSWORD)).willReturn(true);

        final User user = authLoginService.login(email, rawPassword);

        then(userRepository).should(times(1)).findByEmail(any());
        assertAll(
                () -> assertThat(user.username()).isEqualTo(joinedUser.username()),
                () -> assertThat(user.bio()).isEqualTo(joinedUser.bio()),
                () -> assertThat(user.image()).isEqualTo(joinedUser.image()),
                () -> assertThat(user.email()).isEqualTo(joinedUser.email())
        );
    }

    @DisplayName("AuthLoginService 인스턴스 login 실패 테스트")
    @Test
    void login_fail_test() {
        final Email email = new Email(EMAIL);
        final String rawPasswordString = "encode not yet";
        final Password rawPassword = new Password(rawPasswordString);
        final User joinedUser = UserTest.userBuilder(email, new Name(USERNAME), new Password(PASSWORD), new Bio(BIO), new Image(IMAGE));
        given(userRepository.findByEmail(any())).willReturn(Optional.ofNullable(joinedUser));
        given(passwordEncoder.matches(rawPasswordString, PASSWORD)).willReturn(false);

        assertThatThrownBy(() -> authLoginService.login(email, rawPassword))
                .isInstanceOf(PasswordMissMatchException.class)
                .hasMessage("패스워드가 일치하지 않습니다.");
        then(userRepository).should(times(1)).findByEmail(any());
    }

    @DisplayName("AuthLoginService 인스턴스 findByEmail() 실패 테스트")
    @Test
    void findByEmail_fail_test() {
        final Email email = new Email(EMAIL);
        final String rawPasswordString = "encode not yet";
        final Password rawPassword = new Password(rawPasswordString);
        given(userRepository.findByEmail(any())).willReturn(Optional.ofNullable(null));

        assertThatThrownBy(() -> authLoginService.login(email, rawPassword))
                .isInstanceOf(EmailNotFoundException.class)
                .hasMessage(String.format("이메일 : [ %s ] 를 찾을 수 없습니다.", EMAIL));
        then(userRepository).should(times(1)).findByEmail(any());
    }

}