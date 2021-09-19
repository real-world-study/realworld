package com.study.realworld.global.security.config;

import com.study.realworld.global.jwt.filter.JwtAuthenticationFilter;
import com.study.realworld.global.jwt.filter.JwtExceptionHandlerFilter;
import com.study.realworld.global.jwt.error.JwtAuthenticationEntryPoint;
import com.study.realworld.global.security.filter.SecurityExceptionHandlerFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final SecurityExceptionHandlerFilter securityExceptionHandlerFilter;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtExceptionHandlerFilter jwtExceptionHandlerFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    public SecurityConfiguration(final SecurityExceptionHandlerFilter securityExceptionHandlerFilter,
                                 final JwtExceptionHandlerFilter jwtExceptionHandlerFilter,
                                 final JwtAuthenticationFilter jwtAuthenticationFilter,
                                 final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.securityExceptionHandlerFilter = securityExceptionHandlerFilter;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.jwtExceptionHandlerFilter = jwtExceptionHandlerFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    @Override
    public void configure(final WebSecurity web) {
        web.ignoring().antMatchers("/static/**", "/template/**", "/h2-console/**");
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.csrf()
                .disable();

        http.authorizeRequests()
                .antMatchers(POST, "/**").permitAll()
                .anyRequest().authenticated();

        http.sessionManagement()
                .sessionCreationPolicy(STATELESS);

        http.formLogin()
                .disable();

        http.exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint);

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtExceptionHandlerFilter, JwtAuthenticationFilter.class);
        http.addFilterBefore(securityExceptionHandlerFilter, JwtExceptionHandlerFilter.class);
    }

}
