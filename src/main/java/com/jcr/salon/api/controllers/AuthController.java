package com.jcr.salon.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jcr.salon.api.dto.request.LoginRequest;
import com.jcr.salon.api.dto.request.RegisterClientRequest;
import com.jcr.salon.api.dto.request.RegisterEmployeeRequest;
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
   public ResponseEntity<AuthResponse> login(@Validated @RequestBody LoginRequest request) {
      return ResponseEntity.ok(this.authService.login(request));
   }

   @PostMapping(path = "/auth/register")
   public ResponseEntity<AuthResponse> register(
         @RequestBody RegisterRequest request) {

      return ResponseEntity.ok(this.authService.register(request));
   }

   @PostMapping(path = "/auth/register/client")
   public ResponseEntity<AuthResponse> registerClient(
         @RequestBody RegisterClientRequest request) {

      return ResponseEntity.ok(this.authService.registerClient(request));
   }

   @PostMapping(path = "/register/employee")
   public ResponseEntity<AuthResponse> registerEmployee(
         @RequestBody RegisterEmployeeRequest request) {

      return ResponseEntity.ok(this.authService.registerEmployee(request));
   }
}
