package com.jcr.salon.infraestructure.abstract_services;

import java.util.UUID;

import com.jcr.salon.api.dto.request.ServiceRequest;
import com.jcr.salon.api.dto.response.ServiceResponse;

public interface IServiceService extends CrudService<ServiceRequest, ServiceResponse, UUID> { 
  public final String FIELD_BY_SORT = "price";
}
