package com.example.restaurant.config.securityconfig;


import com.example.restaurant.config.securityconfig.filters.AuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration

public class SecurityConfig {


 private final AuthFilter authFilter;

 @Autowired
 public SecurityConfig(AuthFilter authFilter) {
  this.authFilter = authFilter;
 }

 @Bean
 public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
     http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
     http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());

     http.securityMatcher("/**").cors(cors -> cors.configurationSource(corsConfigurationSource()));


     http.authorizeHttpRequests(api -> api
             .requestMatchers("/auth/**").permitAll()
             .requestMatchers("/swagger-ui/**").permitAll()
             .requestMatchers("/v3/**").permitAll()
             .anyRequest().authenticated()
     );
     http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
     return http.build();
 }

@Bean
public CorsConfigurationSource corsConfigurationSource() {
  CorsConfiguration configuration = new CorsConfiguration();
  configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
  configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
  configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
  UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
  source.registerCorsConfiguration("/**", configuration);
  return source;
}

 @Bean
 public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}
}
