package com.study.realworld.global.config;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

import com.study.realworld.global.security.JwtAuthenticationEntryPoint;
import com.study.realworld.global.security.JwtAuthenticationTokenFilter;
import com.study.realworld.global.security.JwtExceptionFilter;
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
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    public WebSecurityConfig(JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter,
        JwtExceptionFilter jwtExceptionFilter,
        JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {

        this.jwtAuthenticationTokenFilter = jwtAuthenticationTokenFilter;
        this.jwtExceptionFilter = jwtExceptionFilter;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
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
            .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            .and()

            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()

            .authorizeRequests()
            .antMatchers(POST, "/api/users", "/api/users/login").permitAll()
            .antMatchers(GET, "/api/profiles/{username}").permitAll()
            .antMatchers(GET, "/api/articles/{slug}", "/api/articles").permitAll()
            .antMatchers(GET, "/api/articles/{slug}/comments").permitAll()
            .antMatchers(GET, "/api/tags").permitAll()
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
