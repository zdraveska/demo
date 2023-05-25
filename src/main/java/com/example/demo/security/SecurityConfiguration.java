package com.example.demo.security;


import com.example.demo.security.jwt.JwtTokenFilter;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import jakarta.servlet.http.HttpServletResponse;
import java.security.SecureRandom;

import static java.lang.String.format;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@AllArgsConstructor
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class SecurityConfiguration  {

  private UserService userService;
  private JwtTokenFilter jwtTokenFilter;


  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf()
            .disable()
            .cors()
            .configurationSource(corsConfiguration())
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(
                    (request, response, ex) ->
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage()))
            .and()
            .authorizeHttpRequests()
            .requestMatchers(HttpMethod.POST, "/api/login")
            .permitAll()
            .requestMatchers(HttpMethod.POST, "/api/register")
            .permitAll()
            .requestMatchers(HttpMethod.POST, "/api/confirmEmail")
            .permitAll()
            .requestMatchers(HttpMethod.POST, "/api/users/resetPassword")
            .permitAll()
            .requestMatchers(HttpMethod.GET, "/api/configuration")
            .permitAll()
            .requestMatchers(HttpMethod.POST, "/api/users/setPassword")
            .permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(
        username ->
            userService
                .findByEmail(username)
                .orElseThrow(
                    () -> new UsernameNotFoundException(format("User: %s, not found", username))));
  }

  @Bean
  CorsConfigurationSource corsConfiguration() {
    CorsConfiguration corsConfig = new CorsConfiguration();
    corsConfig.applyPermitDefaultValues();
    corsConfig.addAllowedHeader("Authorization");
    corsConfig.addAllowedHeader("Access-Control-Allow-Origin");
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfig);
    return source;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(10, new SecureRandom());
  }

}
