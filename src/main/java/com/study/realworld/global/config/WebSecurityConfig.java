package com.study.realworld.global.config;

import static org.springframework.http.HttpMethod.POST;

import com.study.realworld.security.JwtAuthenticationEntiyPoint;
import com.study.realworld.security.JwtAuthenticationTokenFilter;
import com.study.realworld.security.JwtExceptionFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    private final JwtExceptionFilter jwtExceptionFilter;
    private final JwtAuthenticationEntiyPoint jwtAuthenticationEntiyPoint;

    public WebSecurityConfig(JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter,
        JwtExceptionFilter jwtExceptionFilter,
        JwtAuthenticationEntiyPoint jwtAuthenticationEntiyPoint) {

        this.jwtAuthenticationTokenFilter = jwtAuthenticationTokenFilter;
        this.jwtExceptionFilter = jwtExceptionFilter;
        this.jwtAuthenticationEntiyPoint = jwtAuthenticationEntiyPoint;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**", "/templates/**", "/h2/**", "/h2-console/**",
            "/docs/index.html", "/resources/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf()
            .disable();
        http
            .headers()
            .disable();
        http
            .exceptionHandling()
            .authenticationEntryPoint(jwtAuthenticationEntiyPoint)
            .and()

            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()

            .authorizeRequests()
            .antMatchers(POST, "/api/users", "/api/users/login").permitAll()
            .anyRequest().authenticated()
            .and()

            .formLogin()
            .disable();
        http
            .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtExceptionFilter, JwtAuthenticationTokenFilter.class)
        ;
    }

}
