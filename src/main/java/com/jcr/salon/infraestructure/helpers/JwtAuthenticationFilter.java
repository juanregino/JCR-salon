package com.jcr.salon.infraestructure.helpers;


import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
// Filtro para validar el token
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  @Autowired
  private final JwtService jwtService;
  @Autowired
  private final UserDetailsService userDetailsService;
  

  @Override
  public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
   final String token = getTokenFromRequest(request);
    //Si el token es nulo seguir con los filtros de spring
   if (token == null) {
        filterChain.doFilter(request, response);
        return;
    }

    //2. obtener el usuario del token
    String username = jwtService.getUsernameFromToken(token);
    //Si no lo encuentra en el contexto de spring 
     if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      //Obtener el usuario por username por el servicio de usuario
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);

      // Si el token es valido 
      if(this.jwtService.isTokenValid(token, userDetails)) {
        // Crear la autenticacion y la registramos en el contexto de spring
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
          username , null, userDetails.getAuthorities());

          //Asignar detaller de la autenticacion basado e nla fuente de la solicitud 
          /*
           * SetDatails establece detalles adicionales para la autenticacion.
           * Como la direccion IP y la sesion de donde se esta realizando la solicitud
           */
          authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

          /*Registra el token de autenticacion en el contexto de spring*/
         SecurityContextHolder.getContext().setAuthentication(authentication);
      }
     }

     filterChain.doFilter(request, response);
   }


  


  //Metodo para obtener el token del request
  public String getTokenFromRequest(HttpServletRequest request) {
    final  String authHeader = request.getHeader("Authorization");
    //Si el token esta vacio y ademas inicia con la cadena "Bearer" entonces
    if(StringUtils.hasLength(authHeader) && authHeader.startsWith("Bearer")) {
      return authHeader.substring(7);
    }

    return null;
  }






}
