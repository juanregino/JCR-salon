package com.jcr.salon.infraestructure.services;

import java.util.UUID;

import org.springframework.data.domain.Page;

import com.jcr.salon.api.dto.request.ClientRequest;
import com.jcr.salon.api.dto.response.ClientResponse;
import com.jcr.salon.infraestructure.abstract_services.IClientService;
import com.jcr.salon.utils.enums.SortType;

public class ClientService implements IClientService {

  @Override
  public ClientResponse create(ClientRequest request) {
    
    return null;
  }

  @Override
  public ClientResponse delete(UUID id) {
    
    return null;
  }

  @Override
  public ClientResponse findById(UUID id) {
    
    return null;
  }

  @Override
  public Page<ClientResponse> getAll(int Page, int size, SortType sort) {
    
    return null;
  }

  @Override
  public ClientResponse update(ClientRequest request) {
    
    return null;
  }
  
}
