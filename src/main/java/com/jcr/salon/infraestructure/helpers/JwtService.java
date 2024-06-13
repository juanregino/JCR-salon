package com.jcr.salon.infraestructure.helpers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.jcr.salon.domain.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {
  // 1 Crear la firma o clave
  private final String SECRET_KEY = "ZXN0YSBlcyBsYSBzdXBlciBjbGF2ZSBzZWNyZXRhIGRlIGppbWVuYSBwYXJhIGVsIHNhbG9u";

  // 2 metodo para encriptar la clave secreta
  public SecretKey getKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  // 3. Construit el JWT
  public String getToken(Map<String, Object> claims, User user) {

    return Jwts.builder()
        .claims(claims)
        .subject(user.getEmail())
        .issuedAt(new Date(System.currentTimeMillis()))
        .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
        .signWith(this.getKey()) // Esta es la firma del token
        .compact();

  }

  // Metodo para obtener el token
  // Uitlizando sobre escritura de metodos
  public String getToken(User user) {
    // Crear el map de claims
    Map<String, Object> claims = new HashMap<>();
    claims.put("id", user.getId());
    claims.put("role", user.getRole().name());
    // Si le pasamos dos parametros , el sabe que debe retornar es el anterior
    return getToken(claims, user);
  }

  /* Metodo para obtener todos los claims */

  public Claims getAllClaims(String token) {
    return Jwts.parser()// Desarmamos el jwt
        .verifyWith(this.getKey()) // Lo validamos con la firma del token
        .build() // Lo construimos
        .parseSignedClaims(token) // convertir de base64 a json el payload
        .getPayload(); // Extraemos la informaion del payload(Cuerpo del token)

  }

  /*
   * Metodo para obtener un claim especifico, quiere decir que recibe como
   * parametro el token y el claim a buscar
   * Obtiene todos los claims pero retorna uno en especifico.
   * 
   */
  public <T> T getClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = this.getAllClaims(token);

    return claimsResolver.apply(claims);

  }

  // Obtiene el username del token
  public String getUsernameFromToken(String token) {
    return getClaim(token, Claims::getSubject);
  }

  // Obtiene la fecha de expiracion del token
  public Date getExpirationFromToken(String token) {
    return this.getClaim(token, Claims::getExpiration);
  }

  // Metodo para validar el token
  public boolean isTokenExpired(String token) {
    return this.getExpirationFromToken(token).before(new Date());
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
    String username = this.getUsernameFromToken(token);
    return  (username.equals(userDetails.getUsername()) && !this.isTokenExpired(token)) ;

  }
}
