package com.study.realworld.core.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.access.AccessDeniedException;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author JeongJoon Seo
 */
class JwtAccessDeniedHandlerTest {

    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @BeforeEach
    void beforeEach() {
        jwtAccessDeniedHandler = new JwtAccessDeniedHandler();
    }

    @Test
    void handleTest() throws IOException {

        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        AccessDeniedException ex = new AccessDeniedException("");

        jwtAccessDeniedHandler.handle(request, response, ex);

        assertEquals(HttpServletResponse.SC_FORBIDDEN, response.getStatus());
    }
}
