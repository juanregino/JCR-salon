package com.jcr.salon.infraestructure.services;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager; import org.springframework.security.authentication.UsernamePasswordAuthenticationToken; import org.springframework.stereotype.Service;

import com.jcr.salon.api.dto.request.LoginRequest;
import com.jcr.salon.api.dto.request.RegisterClientRequest;
import com.jcr.salon.api.dto.request.RegisterEmployeeRequest;
import com.jcr.salon.api.dto.request.RegisterRequest;
import com.jcr.salon.api.dto.response.AuthResponse;
import com.jcr.salon.domain.entities.ClientEntity;
import com.jcr.salon.domain.entities.Employee;
import com.jcr.salon.domain.entities.User;
import com.jcr.salon.domain.repositories.ClientRepository;
import com.jcr.salon.domain.repositories.EmployeeRepository;
import com.jcr.salon.domain.repositories.UserRepository;
import com.jcr.salon.infraestructure.abstract_services.IAuthService;
import com.jcr.salon.infraestructure.helpers.JwtService;
import com.jcr.salon.utils.enums.Role;

import com.jcr.salon.utils.exceptions.BadRequestException;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
  @Autowired
  private final UserRepository userRepository;
  @Autowired
  private final EmployeeRepository employeeRepository;

  @Autowired
  private final ClientRepository clientRepository;
  @Autowired
  private final AuthenticationManager authenticationManager;
  @Autowired
  private final JwtService jwtService;
  @Override
  public AuthResponse login(LoginRequest request) {
    
    try {
      authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
          request.getEmail(),
          request.getPassword()
        )
      );
    } catch (Exception e) {
      throw new BadRequestException("Invalid credentials");
    }

    User user = this.findUser(request.getEmail());

    return AuthResponse.builder()
        .token(this.jwtService.getToken(user))
        .message("Authenticated successfully")
        .build();
  }

  

  @Override
  public AuthResponse register(RegisterRequest request) {
    
    // 1. Validar que el email no existe
    User exist = this.findUser(request.getEmail());
    if (exist != null) {
      throw new BadRequestException("Email already exists");
    }
    //Construir el usuario
    User user = User.builder()
        .email(request.getEmail())
        .password(request.getPassword())
        .role(Role.ADMIN)
        .build();
//Guardamos el usuario
        user = this.userRepository.save(user);
    return AuthResponse.builder()
        .token(this.jwtService.getToken(user))
        .message("User created successfully")
        .build();
  }

  @Override
  public AuthResponse registerClient(RegisterClientRequest request) {
    User user = validateUser(request.getEmail(), request.getPassword(), Role.CLIENT);
    user = this.userRepository.save(user);
    ClientEntity client = ClientEntity.builder()
        .name(request.getName())
        .lastName(request.getLastName())
        .phone(request.getPhone())
        .email(request.getEmail())
        .user(user)
        .build();

        this.clientRepository.save(client);
    return AuthResponse.builder()
        .token(this.jwtService.getToken(user))
        .message("Client created successfully")
        .build();
    
  }

  @Override
  public AuthResponse registerEmployee(RegisterEmployeeRequest request) {
    User user = validateUser(request.getEmail(), request.getPassword(), Role.EMPLOYEE);
    user = this.userRepository.save(user);
    Employee employee = Employee.builder()
        .name(request.getName())
        .lastName(request.getLastName())
        .phone(request.getPhone())
        .email(request.getEmail())
        .role(request.getRole())
        .user(user)
        .appointments(new ArrayList<>())
        .build();

        this.employeeRepository.save(employee);
    return AuthResponse.builder()
        .token(this.jwtService.getToken(user))
        .message("Employee created successfully")
        .build();
  }

  private User findUser(String email) {
    return this.userRepository.findByEmail(email).orElse(null);
  }

  private User validateUser(String email, String password , Role role) {
   //1. Validar que el email no existe
   User exist = this.findUser(email); 
   if (exist != null) {
     throw new BadRequestException("Email already exists");
   }
   //Construir el usuario
   User user = User.builder()
       .email(email)
       .password(password)
       .role(role)
       .build();
   return user;
  }
  
}
