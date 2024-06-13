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
 * Este metodo crea y configura un DaoAuthenticationProvider, que es una de las implementaciones mas comunes para proveer datos a nuestra app, de esta forma guardamos las credenciales de los usuarios 
 * Guardaremos toda la informacion y el tipo de cifrado que tiene su contraseña             
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
  //Le decimos que ya no va a codificar con lo que trae por defecto para eso se utlizia el @Bean
  // Ahora va a encriptar con BCrypt 
   @Bean 
   public PasswordEncoder passwordEncoder(){
     return new BCryptPasswordEncoder();
   }
}
