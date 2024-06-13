package com.jcr.salon.infraestructure.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
//Se pone transaccional porque hay operaciones que puede fallar como por ejemplo que no se guarda un usuario pero si un cliente, entonces esto nos asegura que se cumplan todas las operaciones
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

  @Autowired
  private final PasswordEncoder passwordEncoder;

  @Override
  public AuthResponse login(LoginRequest request) {

    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(
              request.getEmail(),
              request.getPassword()));
    } catch (Exception e) {
      throw new BadRequestException("Invalid credentials");
    }

    // Si el usuario se autentico correctamente
    User user = this.findUser(request.getEmail());

    if (user == null) {
      throw new BadRequestException("Invalid User");
    }

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
    // Construir el usuario
    User user = User.builder()
        .email(request.getEmail())
        // guardamos la contraseña codificada en la base de datos
        .password(passwordEncoder.encode(request.getPassword()))
        .role(Role.ADMIN)
        .build();
    // Guardamos el usuario en la base de datos
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
        .appointments(new ArrayList<>())
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

  private User validateUser(String email, String password, Role role) {
    // 1. Validar que el email no existe
    User exist = this.findUser(email);
    if (exist != null) {
      throw new BadRequestException("Email already exists");
    }
    // Construir el usuario
    User user = User.builder()
        .email(email)
        .password(password)
        .role(role)
        .build();
    return user;
  }

}
