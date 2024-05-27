package com.jcr.salon.infraestructure.abstract_services;

import com.jcr.salon.api.dto.request.LoginRequest;
import com.jcr.salon.api.dto.request.RegisterClientRequest;
import com.jcr.salon.api.dto.request.RegisterEmployeeRequest;
import com.jcr.salon.api.dto.request.RegisterRequest;
import com.jcr.salon.api.dto.response.AuthResponse;

public interface IAuthService {
  public AuthResponse login(LoginRequest request);

  public AuthResponse register(RegisterRequest request);
  public AuthResponse registerEmployee(RegisterEmployeeRequest request);
  public AuthResponse registerClient(RegisterClientRequest request);
}
