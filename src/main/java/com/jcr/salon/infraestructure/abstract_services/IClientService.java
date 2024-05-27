package com.jcr.salon.infraestructure.abstract_services;

import java.util.UUID;

import com.jcr.salon.api.dto.request.ClientRequest;
import com.jcr.salon.api.dto.response.ClientResponse;

public interface IClientService extends CrudService<ClientRequest, ClientResponse, UUID> {
  public final String FIELD_BY_SORT = "name";
}
