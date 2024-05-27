package com.jcr.salon.api.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jcr.salon.api.dto.request.RegisterRequest;
import com.jcr.salon.api.dto.response.AuthResponse;
import com.jcr.salon.infraestructure.abstract_services.IAuthService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthController {
   @Autowired
   private final IAuthService authService;
   @PostMapping(path = "/auth/login")
   public String login() {
      return "Login";
   }
   @PostMapping(path = "/auth/register")
   public ResponseEntity<AuthResponse> register(
      @RequestBody RegisterRequest request
   ) {

      return ResponseEntity.ok(this.authService.register(request));
   }
}
