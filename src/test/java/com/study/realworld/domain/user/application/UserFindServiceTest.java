package com.study.realworld.domain.user.application;

import com.study.realworld.domain.user.domain.*;
import com.study.realworld.domain.user.error.exception.EmailNotFoundException;
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
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserFindServiceTest {

    @Mock private UserRepository userRepository;
    @InjectMocks private UserFindService userFindService;

    @DisplayName("JwtUserDetailsService 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final UserFindService userFindService = new UserFindService(userRepository);

        assertAll(
                () -> assertThat(userFindService).isNotNull(),
                () -> assertThat(userFindService).isInstanceOf(UserFindService.class)
        );
    }

    @DisplayName("JwtUserDetailsService 인스턴스 loadUserByUsername 테스트")
    @Test
    void findByEmail_test() {
        final User user = userBuilder(new Email(EMAIL), new Name(USERNAME), new Password(PASSWORD), new Bio(BIO), new Image(IMAGE));
        given(userRepository.findByEmail(any())).willReturn(Optional.ofNullable(user));

        final User findUser = userFindService.findUserByEmail(EMAIL);
        assertAll(
                () -> assertThat(findUser.email().email()).isEqualTo(EMAIL),
                () -> assertThat(findUser.password().password()).isEqualTo(PASSWORD)
        );
    }

    @DisplayName("JwtUserDetailsService 인스턴스 findByEmail() 실패 테스트")
    @Test
    void fail_findByEmail_test() {
        given(userRepository.findByEmail(any())).willReturn(Optional.ofNullable(null));

        assertThatThrownBy(() -> userFindService.findUserByEmail(EMAIL))
                .isInstanceOf(EmailNotFoundException.class)
                .hasMessage(String.format("이메일 : [ %s ] 를 찾을 수 없습니다.", EMAIL));
    }

}