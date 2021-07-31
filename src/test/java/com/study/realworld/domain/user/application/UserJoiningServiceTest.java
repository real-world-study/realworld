package com.study.realworld.domain.user.application;

import com.study.realworld.domain.user.domain.UserRepository;
import com.study.realworld.domain.user.dto.UserJoinRequest;
import com.study.realworld.domain.user.dto.UserJoinResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.study.realworld.domain.user.domain.UserTest.USER;
import static com.study.realworld.domain.user.dto.UserJoinRequestTest.USER_JOIN_REQUEST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class UserJoiningServiceTest {

    @Mock private UserRepository userRepository;
    @InjectMocks private UserJoiningService userJoiningService;

    @DisplayName("UserJoiningService 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final UserJoiningService userJoiningService = new UserJoiningService(userRepository);

        assertAll(
                () -> assertThat(userJoiningService).isNotNull(),
                () -> assertThat(userJoiningService).isExactlyInstanceOf(UserJoiningService.class)
        );
    }

    @DisplayName("UserJoiningService 인스턴스 join() 테스트")
    @Test
    void join_test() {
        given(userRepository.save(any())).willReturn(USER);

        final UserJoinRequest userJoinRequest = USER_JOIN_REQUEST;
        final UserJoinResponse userJoinResponse = userJoiningService.join(userJoinRequest);

        then(userRepository).should(times(1)).save(any());
        assertAll(
                () -> assertThat(userJoinResponse).isNotNull(),
                () -> assertThat(userJoinResponse).isExactlyInstanceOf(UserJoinResponse.class)
        );
    }

}