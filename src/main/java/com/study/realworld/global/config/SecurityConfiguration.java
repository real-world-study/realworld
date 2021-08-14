package com.study.realworld.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        csrfConfigure(http);
        authorizationConfigure(http);
        sessionConfigure(http);
    }

    protected void csrfConfigure(final HttpSecurity http) throws Exception {
        http.csrf().disable(); // REST API 이므로 csrf 가 필요 없다.
    }

    protected void authorizationConfigure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/*").permitAll()
                .anyRequest().authenticated();
    }

    protected void sessionConfigure(final HttpSecurity http) throws Exception {
        http.sessionManagement()
                .sessionCreationPolicy(STATELESS); // 세션 및 세션 ID 할담 비홠성화
    }

}
