package com.jcr.salon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
  //Van a ir todas las rutas que van a ser publicas
  private final String[] PUBLIC_RESOURCES = { "/auth/**" , "/services/public/get" }; 
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    
    return http.csrf(csrf -> csrf.disable()) // Desabilitar protección csrf -> Statelest
    .authorizeHttpRequests(authRequest -> authRequest
        .requestMatchers(PUBLIC_RESOURCES).permitAll() // Configurar rutas publicas
        .anyRequest().authenticated()).build();
    
  }
}
