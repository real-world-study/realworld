package com.tistory.povia.realworld.auth.controller;

import com.tistory.povia.realworld.auth.domain.AuthenticationPrincipal;
import com.tistory.povia.realworld.auth.infra.AuthorizationExtractor;
import com.tistory.povia.realworld.auth.service.AuthService;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthService authService;

    public AuthenticationPrincipalArgumentResolver(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory)
            throws Exception {
        String credentials =
                AuthorizationExtractor.extract(
                        webRequest.getNativeRequest(HttpServletRequest.class));
        return authService.findByToken(credentials);
    }
}
