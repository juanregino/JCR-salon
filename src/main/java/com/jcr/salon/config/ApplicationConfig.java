package com.jcr.salon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.jcr.salon.domain.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {
  // Inyectamos el repositorio del usuario 
  @Autowired
  private final UserRepository userRepository;   
  /*
   * AuthenticationManager permite el manejo del usuario en toda la app
   * define un bean de tipo AuthenticationManager y utiliza configuración por
   * defecto de spring security
   */
   @Bean
   public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
     return config.getAuthenticationManager();
   }

   /*
    * Configuramos el provider con un DaoProvider que es una implementación que
    * tiene como finalidad proveer la informacion del usuario a la app
    */
   @Bean
   public AuthenticationProvider authenticationProvider(){
    var authenticationProvider = new DaoAuthenticationProvider();

    authenticationProvider.setPasswordEncoder(this.passwordEncoder());
    authenticationProvider.setUserDetailsService(this.userDetailsService());

    return authenticationProvider;
   }

   @Bean
   public UserDetailsService userDetailsService(){
    return username -> this.userRepository.findByEmail(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
   }

   @Bean 
   public PasswordEncoder passwordEncoder(){
     return new BCryptPasswordEncoder();
   }
}
