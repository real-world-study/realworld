package com.tistory.povia.realworld.security;

import com.tistory.povia.realworld.auth.infra.JwtTokenProvider;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * filter는 스프링에 들어오기 전 request를 받아서 가장 먼저 처리하는 순서를 가진다. Security에서는 인증을 위해서
 * UsernamePasswordAuthenticationFilter라는 Filter를 가지는데 이는 default한 filter이다. 즉, login 당시에는
 * security없이 아이디, 비밀번호를 통해 로그인하고 이후에는 항상 header에서 jwt를 받기 때문에 filter에 들어올 때 jwt를 받아서 안에 있는 식별자 값을
 * 꺼내오는 filter를 여기서 구현한 것이다. "Token 토큰내용"의 형태가 기본 스팩이기 때문에 header를 받아 이를 처리하고, JwtProvider에서 jwt를
 * 복호화하여 내부에 들어있는 식별자 정보를 찾는다. 그 후 controller단에서 인증된 정보를 사용하기 편하게 하기 위해서 SecurityContextHolder내에
 * 식별자와 토큰을 저장해둔다.
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        String[] headers = header.split(" ");
        if (headers.length == 2 && headers[0].equals("Token") && StringUtils.hasText(headers[1])) {
            String accessToken = headers[1];
            String address = jwtTokenProvider.getPayload(accessToken);
            JwtAuthentication authentication = new JwtAuthentication(address, accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
