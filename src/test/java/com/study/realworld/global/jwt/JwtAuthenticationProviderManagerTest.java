package com.study.realworld.global.jwt;

import com.study.realworld.domain.user.domain.persist.User;
import com.study.realworld.domain.user.domain.vo.*;
import com.study.realworld.global.jwt.authentication.JwtAuthentication;
import com.study.realworld.global.jwt.authentication.JwtAuthenticationProvider;
import com.study.realworld.global.jwt.authentication.JwtAuthenticationProviderManager;
import com.study.realworld.global.jwt.error.exception.JwtProviderNotSupportTypeException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

import static com.study.realworld.domain.auth.infrastructure.TokenProviderTest.testToken;
import static com.study.realworld.domain.user.domain.vo.BioTest.BIO;
import static com.study.realworld.domain.user.domain.vo.EmailTest.EMAIL;
import static com.study.realworld.domain.user.domain.vo.ImageTest.IMAGE;
import static com.study.realworld.domain.user.domain.vo.NameTest.USERNAME;
import static com.study.realworld.domain.user.domain.vo.PasswordTest.PASSWORD;
import static com.study.realworld.domain.user.domain.persist.UserTest.userBuilder;
import static com.study.realworld.global.jwt.authentication.JwtAuthentication.initAuthentication;
import static com.study.realworld.global.jwt.authentication.JwtAuthentication.ofUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class JwtAuthenticationProviderManagerTest {

    @Mock private JwtAuthenticationProvider jwtAuthenticationProvider;
    @InjectMocks private JwtAuthenticationProviderManager jwtAuthenticationProviderManager;

    @DisplayName("JwtAuthenticationProviderManager 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final JwtAuthenticationProviderManager jwtAuthenticationProviderManager = new JwtAuthenticationProviderManager(jwtAuthenticationProvider);

        assertAll(
                () -> assertThat(jwtAuthenticationProviderManager).isNotNull(),
                () -> assertThat(jwtAuthenticationProviderManager)
                        .isExactlyInstanceOf(JwtAuthenticationProviderManager.class)
        );
    }

    @DisplayName("JwtAuthenticationProviderManager 인스턴스 validateSupportable() 실패 테스트")
    @Test
    void fail_validateSupportable_test() {
        doReturn(false).when(jwtAuthenticationProvider).supports(any());

        final TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(USERNAME, PASSWORD);
        assertThatThrownBy(() -> jwtAuthenticationProviderManager.authenticate(testingAuthenticationToken))
                .isInstanceOf(JwtProviderNotSupportTypeException.class)
                .hasMessage("[ TestingAuthenticationToken ] is not supported in JwtProvider");
    }

    @DisplayName("JwtAuthenticationProviderManager 인스턴스 support() 테스트")
    @Test
    void authenticate_test() {
        final Long principal = 1L;
        final Password password = new Password(PASSWORD);
        final User user = userBuilder(new Email(EMAIL), new Name(USERNAME), password, new Bio(BIO), new Image(IMAGE));
        ReflectionTestUtils.setField(user, "id", principal);
        final JwtAuthentication returnedAuthentication = ofUser(user);
        doReturn(returnedAuthentication).when(jwtAuthenticationProvider).authenticate(any());
        doReturn(true).when(jwtAuthenticationProvider).supports(any());

        final JwtAuthentication jwtAuthentication = initAuthentication(testToken());
        final Authentication authentication = jwtAuthenticationProviderManager.authenticate(jwtAuthentication);
        assertAll(
                () -> assertThat(authentication).isNotNull(),
                () -> assertThat(authentication.getPrincipal()).isEqualTo(principal),
                () -> assertThat(authentication.getCredentials()).isEqualTo(password)
        );
    }

}