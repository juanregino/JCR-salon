package com.jcr.salon.infraestructure.abstract_services;

import java.util.UUID;

import com.jcr.salon.api.dto.request.AppointmentRequest;
import com.jcr.salon.api.dto.response.AppointmentResponse;

public interface IAppointmentService extends CrudService<AppointmentRequest, AppointmentResponse, UUID> { 
  public final String FIELD_BY_SORT = "date";
}
