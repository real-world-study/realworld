package com.tistory.povia.realworld.configuration;

import com.tistory.povia.realworld.security.JwtAuthenticationEntryPoint;
import com.tistory.povia.realworld.security.JwtRequestFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final UserDetailsService userDetailsService;

    private final JwtRequestFilter jwtRequestFilter;

    //  private final MyUserDetailsService userDetailService;
    //
    public SecurityConfig(
            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
            UserDetailsService userDetailsService,
            JwtRequestFilter jwtRequestFilter) {
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
        this.userDetailsService = userDetailsService;
        this.jwtRequestFilter = jwtRequestFilter;
    }
    //
    //  @Override
    //  protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
    //    auth.userDetailsService(userService);
    //  }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // We don't need CSRF for this example
        http.csrf()
                .disable()
                // dont authenticate this particular request
                .authorizeRequests()
                .antMatchers("/authenticate")
                .permitAll()
                .anyRequest()
                // all other requests need to be authenticated
                .authenticated()
                .and()
                // make sure we use stateless session; session won't be used to
                // store user's state.
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    //  @Override
    //  protected void configure(HttpSecurity http) throws Exception {
    //    http
    //      .csrf()
    //      .disable()
    //      .headers()
    //      .disable()
    //      .exceptionHandling()
    //      .accessDeniedHandler(accessDeniedHandler)
    //      .authenticationEntryPoint(unauthorizedHandler)
    //      .and()
    //      .sessionManagement()
    //      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    //      .and()
    //      .authorizeRequests()
    //      .antMatchers("/api/_hcheck").permitAll()
    //      .antMatchers("/api/auth").permitAll()
    //      .antMatchers("/api/user/join").permitAll()
    //      .antMatchers("/api/user/exists").permitAll()
    //      .antMatchers("/api/**").hasRole(Role.USER.name())
    //      .accessDecisionManager(accessDecisionManager())
    //      .anyRequest().permitAll()
    //      .and()
    //      .formLogin()
    //      .disable();
    //    http
    //      .addFilterBefore(jwtAuthenticationTokenFilter(),
    // UsernamePasswordAuthenticationFilter.class);
    //  }

}
