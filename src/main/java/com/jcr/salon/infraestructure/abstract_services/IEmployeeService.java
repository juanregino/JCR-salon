package com.jcr.salon.infraestructure.abstract_services;

import java.util.UUID;

import com.jcr.salon.api.dto.request.EmployeeRequest;
import com.jcr.salon.api.dto.response.EmployeeResponse;

public interface IEmployeeService extends CrudService<EmployeeRequest, EmployeeResponse, UUID> {
  public final String FIELD_BY_SORT = "name";
}
