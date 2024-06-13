package com.jcr.salon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jcr.salon.infraestructure.helpers.JwtAuthenticationFilter;
import com.jcr.salon.utils.enums.Role;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  @Autowired
  private final AuthenticationProvider authenticationProvider;
  @Autowired
  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  // Van a ir todas las rutas que van a ser publicas
  private final String[] PUBLIC_RESOURCES = { "/auth/**", "/services/public/get" , "/swagger-ui/**", "v3/api-docs/**",
      "/v3/api-docs.yaml", "/swagger-ui.html"};

    private final String[] ADMIN_RESOURCES = { "/register/employee"};

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    return http.csrf(csrf -> csrf.disable()) // Desabilitar protección csrf -> Statelest
        .authorizeHttpRequests(authRequest -> authRequest
        .requestMatchers(ADMIN_RESOURCES).hasAuthority(Role.ADMIN.name())    // Le configuramos para que solo tenga acceso si tiene el rol admin
        .requestMatchers(PUBLIC_RESOURCES).permitAll() // Configurar rutas publicas
            .anyRequest().authenticated())
        .sessionManagement(sessioManager -> sessioManager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(this.authenticationProvider)
        .addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .build();

  }
}
