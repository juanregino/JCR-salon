package com.jcr.salon.infraestructure.services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jcr.salon.api.dto.request.ClientRequest;
import com.jcr.salon.api.dto.response.AppointmentBasicResponse;
import com.jcr.salon.api.dto.response.ClientResponse;
import com.jcr.salon.api.dto.response.EmployeeResponse;
import com.jcr.salon.api.dto.response.ServiceResponse;
import com.jcr.salon.domain.entities.Appointment;
import com.jcr.salon.domain.entities.ClientEntity;

import com.jcr.salon.domain.repositories.ClientRepository;
import com.jcr.salon.infraestructure.abstract_services.IClientService;
import com.jcr.salon.utils.enums.SortType;
import com.jcr.salon.utils.exceptions.BadRequestException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class ClientService implements IClientService {
  @Autowired
  private final ClientRepository clientRepository;

  @Override
  public ClientResponse create(ClientRequest request) {
    ClientEntity client = this.toEntity(request);
    // Como el request no trae una lista de appointments, lo creamos y lo asignamos
    // a la entidad
    client.setAppointments(new ArrayList<>());
    return this.toResponse(this.clientRepository.save(client));

  }

  @Override
  public void delete(UUID id) {
    ClientEntity client = this.find(id);
    this.clientRepository.delete(client);
  }

  @Override
  public ClientResponse findById(UUID id) {

    return this.toResponse(this.find(id));
  }

  @Override
  public Page<ClientResponse> getAll(int page, int size, SortType sort) {
    if (page < 0)
      page = 0;
    PageRequest pagination = null;

    switch (sort) {
      case ASC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
      case DESC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
      case NONE -> pagination = PageRequest.of(page, size);
    }

    return this.clientRepository.findAll(pagination)
        .map(this::toResponse);
  }

  @Override
  public ClientResponse update(ClientRequest request, UUID id) {
    // Busco si existe en la base de datos y lo traigo para poder actualizarlo
    ClientEntity client = this.find(id);

    // Lo convierto de request a entidad
    ClientEntity clientUpdate = this.toEntity(request);
    // Le asigno a la entidad el id del cliente que se quiere actualizar
    clientUpdate.setId(id);
    // Luego le asigno la lista de appointments del que nos trajo de la base de
    // datos , al request que convertimos a entidad
    clientUpdate.setAppointments(client.getAppointments());
    return this.toResponse(this.clientRepository.save(clientUpdate));
  }

  private ClientResponse toResponse(ClientEntity entity) {
    // Traemos la lista de appointments de la entidad y la debemos convertir a una
    // lista de response, para esto creamos el otro metodo que nos ayuda a cumplir
    // este paso
    List<AppointmentBasicResponse> appointments = entity.getAppointments()
        .stream()
        .map(this::toResponseAppointment)
        .collect(Collectors.toList());
    // Construimos el response , con la lista previamente convertida a response
    return ClientResponse.builder()
        .id(entity.getId())
        .name(entity.getName())
        .lastName(entity.getLastName())
        .phone(entity.getPhone())
        .email(entity.getEmail())
        .appointments(appointments)
        .build();
  }

  private AppointmentBasicResponse toResponseAppointment(Appointment entity) {
    // Traigo el service y el empleado de la entidad
    ServiceResponse service = new ServiceResponse();
    BeanUtils.copyProperties(entity.getService(), service);

    EmployeeResponse employee = new EmployeeResponse();
    BeanUtils.copyProperties(entity.getEmployee(), employee);
    return AppointmentBasicResponse.builder()
        .id(entity.getId())
        .date(entity.getDate())
        .duration(entity.getDuration())
        .comment(entity.getComment())
        .service(service)
        .employee(employee)
        .build();
  }

  private ClientEntity toEntity(ClientRequest request) {
    // Traigo los datos del request y lo convierto en la entidad
    ClientEntity entity = ClientEntity.builder()
        .name(request.getName())
        .lastName(request.getLastName())
        .phone(request.getPhone())
        .email(request.getEmail())
        .build();
    return entity;
  }

  private ClientEntity find(UUID id) {
    return this.clientRepository.findById(id).orElseThrow(() -> new BadRequestException("Client not found"));
  }
}
