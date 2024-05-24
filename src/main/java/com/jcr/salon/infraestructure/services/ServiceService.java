package com.jcr.salon.infraestructure.services;

import java.util.UUID;

import org.springframework.data.domain.Page;

import com.jcr.salon.api.dto.request.ServiceRequest;
import com.jcr.salon.api.dto.response.ServiceResponse;
import com.jcr.salon.infraestructure.abstract_services.IServiceService;
import com.jcr.salon.utils.enums.SortType;

public class ServiceService implements IServiceService {

  @Override
  public ServiceResponse create(ServiceRequest request) {
    
    return null;
  }

  @Override
  public ServiceResponse delete(UUID id) {
    
    return null;
  }

  @Override
  public ServiceResponse findById(UUID id) {
    
    return null;
  }

  @Override
  public Page<ServiceResponse> getAll(int Page, int size, SortType sort) {
    
    return null;
  }

  @Override
  public ServiceResponse update(ServiceRequest request) {
    
    return null;
  }
  
}
