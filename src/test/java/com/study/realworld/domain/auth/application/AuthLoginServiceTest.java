package com.study.realworld.domain.auth.application;

import com.study.realworld.domain.user.domain.*;
import com.study.realworld.domain.user.dto.UserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.study.realworld.domain.user.domain.BioTest.BIO;
import static com.study.realworld.domain.user.domain.EmailTest.EMAIL;
import static com.study.realworld.domain.user.domain.ImageTest.IMAGE;
import static com.study.realworld.domain.user.domain.NameTest.USERNAME;
import static com.study.realworld.domain.user.domain.PasswordTest.PASSWORD;
import static com.study.realworld.domain.user.dto.UserResponse.ofUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AuthLoginServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private AuthLoginService authLoginService;

    @DisplayName("AuthLoginService 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final AuthLoginService authLoginService = new AuthLoginService(userRepository);

        assertAll(
                () -> assertThat(authLoginService).isNotNull(),
                () -> assertThat(authLoginService).isExactlyInstanceOf(AuthLoginService.class)
        );
    }

    @DisplayName("AuthLoginService 인스턴스 login 테스트")
    @Test
    void login_test() {
        final User user = UserTest.userBuilder(new Email(EMAIL), new Name(USERNAME), new Password(PASSWORD), new Bio(BIO), new Image(IMAGE));
        final UserResponse userResponse = ofUser(user);


        assertAll(
                () -> assertThat(authLoginService).isNotNull(),
                () -> assertThat(authLoginService).isExactlyInstanceOf(AuthLoginService.class)
        );
    }


}