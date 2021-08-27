package com.study.realworld.global.config.security.jwt;

import com.study.realworld.domain.user.domain.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import java.io.IOException;

import static com.study.realworld.domain.auth.infrastructure.TokenProviderTest.testToken;
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
import static org.mockito.Mockito.mock;
import static org.springframework.security.core.context.SecurityContextHolder.getContext;

@ExtendWith(MockitoExtension.class)
class JwtFilterTest {

    @Mock private JwtProviderManager jwtProviderManager;
    @InjectMocks private JwtFilter jwtFilter;

    @DisplayName("JwtFilter 인스턴스 생성자 테스트")
    @Test
    void constructor_test() {
        final JwtFilter jwtFilter = new JwtFilter(jwtProviderManager);

        assertAll(
                () -> assertThat(jwtFilter).isNotNull(),
                () -> assertThat(jwtFilter).isExactlyInstanceOf(JwtFilter.class)
        );
    }

    @DisplayName("JwtFilter 인스턴스 doFilter 테스트")
    @Test
    void doFilter_test() throws ServletException, IOException {
        final MockHttpServletRequest request = new MockHttpServletRequest();
        final MockHttpServletResponse response = new MockHttpServletResponse();
        final FilterChain chain = mock(FilterChain.class);
        request.addHeader("Authorization", testToken());

        final User user = userBuilder(new Email(EMAIL), new Name(USERNAME), new Password(PASSWORD), new Bio(BIO), new Image(IMAGE));
        final JwtAuthentication jwtAuthentication = JwtAuthentication.ofUser(user);
        doReturn(jwtAuthentication).when(jwtProviderManager).authenticate(any());

        jwtFilter.doFilter(request, response, chain);
        final Authentication authentication = getContext().getAuthentication();
        assertAll(
                () -> assertThat(authentication).isNotNull(),
                () -> assertThat(authentication.getPrincipal()).isEqualTo(EMAIL),
                () -> assertThat(authentication.getCredentials()).isEqualTo(PASSWORD)
        );
    }

}