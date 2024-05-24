package com.jcr.salon.infraestructure.services;

import java.util.UUID;

import org.springframework.data.domain.Page;

import com.jcr.salon.api.dto.request.EmployeeRequest;
import com.jcr.salon.api.dto.response.EmployeeResponse;
import com.jcr.salon.infraestructure.abstract_services.IEmployeeService;
import com.jcr.salon.utils.enums.SortType;

public class EmployeeService implements IEmployeeService  {

  @Override
  public EmployeeResponse create(EmployeeRequest request) {
    
    return null;
  }

  @Override
  public EmployeeResponse delete(UUID id) {
    
    return null;
  }

  @Override
  public EmployeeResponse findById(UUID id) {
    
    return null;
  }

  @Override
  public Page<EmployeeResponse> getAll(int Page, int size, SortType sort) {
    
    return null;
  }

  @Override
  public EmployeeResponse update(EmployeeRequest request) {
    
    return null;
  }
   
}
