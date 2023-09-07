package com.iam.serviceacquisition.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return  http.cors()
            .and()
            .csrf().disable()
            .addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
            .authorizeHttpRequests().requestMatchers(
                    "/api/auth/**", "/swagger-ui-custom.html" ,
                    "/swagger-ui.html", "/swagger-ui/**",
                    "/v3/api-docs/**", "/webjars/**",
                    "/swagger-ui/index.html","/api-docs/**").permitAll()
            .and()
            .authorizeHttpRequests().requestMatchers("/**").permitAll()
            .anyRequest().authenticated().and()
            .cors().and().csrf().disable()
            .build();
  }

}
