package com.study.realworld.security;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.io.IOException;
import java.util.Optional;
import java.util.regex.Pattern;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private static final Pattern SCHEMA = Pattern.compile("^Token$", Pattern.CASE_INSENSITIVE);

    private final JwtService jwtService;

    public JwtAuthenticationTokenFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        resolveToken(request).ifPresent(
            token -> jwtService.getUser(token).ifPresent(
                user -> {
                    JwtAuthentication authentication = new JwtAuthentication(user.id(),
                        token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            )
        );

        filterChain.doFilter(request, response);
    }

    public Optional<String> resolveToken(HttpServletRequest request) {
        final String header = request.getHeader(AUTHORIZATION);
        if (Strings.isEmpty(header)) {
            return Optional.empty();
        }
        return checkMatch(header.split(" "));
    }

    private Optional<String> checkMatch(String[] parts) {
        if (parts.length == 2 && SCHEMA.matcher(parts[0]).matches()) {
            return Optional.ofNullable(parts[1]);
        }
        return Optional.empty();
    }

}
