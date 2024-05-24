package com.jcr.salon.infraestructure.services;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.jcr.salon.api.dto.request.AppointmentRequest;
import com.jcr.salon.api.dto.response.AppointmentResponse;
import com.jcr.salon.infraestructure.abstract_services.IAppointmentService;
import com.jcr.salon.utils.enums.SortType;

@Service
public class AppointmentService implements IAppointmentService {

  @Override
  public AppointmentResponse create(AppointmentRequest request) {
    
    return null;
  }

  @Override
  public AppointmentResponse delete(UUID id) {
    
    return null;
  }

  @Override
  public AppointmentResponse findById(UUID id) {
    
    return null;
  }

  @Override
  public Page<AppointmentResponse> getAll(int Page, int size, SortType sort) {
    
    return null;
  }

  @Override
  public AppointmentResponse update(AppointmentRequest request) {
    
    return null;
  }
  
}
