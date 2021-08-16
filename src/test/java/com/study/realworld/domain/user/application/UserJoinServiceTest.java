package com.study.realworld.domain.user.application;

import com.study.realworld.domain.auth.dto.ResponseToken;
import com.study.realworld.domain.auth.dto.token.AccessToken;
import com.study.realworld.domain.auth.dto.token.RefreshToken;
import com.study.realworld.domain.user.domain.*;
import com.study.realworld.domain.user.dto.UserJoinRequest;
import com.study.realworld.domain.user.dto.UserJoinRequestTest;
import com.study.realworld.domain.user.dto.UserResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.study.realworld.domain.user.domain.BioTest.BIO;
import static com.study.realworld.domain.user.domain.EmailTest.EMAIL;
import static com.study.realworld.domain.user.domain.ImageTest.IMAGE;
import static com.study.realworld.domain.user.domain.NameTest.USERNAME;
import static com.study.realworld.domain.user.domain.PasswordTest.PASSWORD;
import static com.study.realworld.domain.user.domain.UserTest.userBuilder;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class UserJoinServiceTest {

    @Mock private UserRepository userRepository;
    @Mock private PasswordEncoder passwordEncoder;
    @InjectMocks private UserJoinService userJoinService;

    @DisplayName("UserJoinService 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final UserJoinService userJoinService = new UserJoinService(userRepository, passwordEncoder);

        assertAll(
                () -> assertThat(userJoinService).isNotNull(),
                () -> assertThat(userJoinService).isExactlyInstanceOf(UserJoinService.class)
        );
    }

    @DisplayName("UserJoinService 인스턴스 join() 테스트")
    @Test
    void join_test() {
        final String notEncodedPassword = "notEncodedPassword";
        final User user = userBuilder(new Email(EMAIL), new Name(USERNAME), new Password(PASSWORD), new Bio(BIO), new Image(IMAGE));
        given(userRepository.save(any())).willReturn(user);
        given(passwordEncoder.encode(any())).willReturn(PASSWORD);

        final UserJoinRequest userJoinRequest = UserJoinRequestTest.userJoinRequest(new Name(USERNAME), new Email(EMAIL), new Password(notEncodedPassword));
        final User joinedUser = userJoinService.join(userJoinRequest.toUser());
        final ResponseToken responseToken = new ResponseToken(new AccessToken("accessToken"), new RefreshToken("responseToken"));
        final UserResponse userResponse = UserResponse.fromUserWithToken(joinedUser, responseToken);

        then(userRepository).should(times(1)).save(any());
        assertAll(
                () -> assertThat(userResponse).isNotNull(),
                () -> assertThat(userResponse).isExactlyInstanceOf(UserResponse.class)
        );
    }

}