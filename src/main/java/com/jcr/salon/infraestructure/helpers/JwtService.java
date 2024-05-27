package com.jcr.salon.infraestructure.helpers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.SecretKey;


import org.springframework.stereotype.Component;

import com.jcr.salon.domain.entities.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {
  //1 Crear la firma o clave
  private final String SECRET_KEY = "ZXN0YSBlcyBsYSBzdXBlciBjbGF2ZSBzZWNyZXRhIGRlIGppbWVuYSBwYXJhIGVsIHNhbG9u";

  //2 metodo para encriptar la clave secreta
  public SecretKey getKey(){
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  //3. Construit el JWT
  public String getToken(Map<String, Object> claims , User user){
   
    return Jwts.builder()
               .claims(claims)
               .subject(user.getEmail())
               .issuedAt(new Date(System.currentTimeMillis()))
               .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 ))
               .signWith(this.getKey()) //Esta es la firma del token
               .compact();
               
  
  }

  //Metodo para obtener el token
  //Uitlizando sobre escritura de metodos 
  public String getToken(User user){
   //Crear el map de claims
   Map<String, Object> claims = new HashMap<>();
   claims.put("id", user.getId());
   claims.put("role", user.getRole().name()); 
   //Si le pasamos dos parametros , el sabe que debe retornar es el anterior 
   return getToken(claims , user);
  }
}
