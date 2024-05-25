package com.jcr.salon.infraestructure.services;

import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jcr.salon.api.dto.request.AppointmentRequest;
import com.jcr.salon.api.dto.response.AppointmentResponse;
import com.jcr.salon.api.dto.response.ClientBasicResponse;
import com.jcr.salon.api.dto.response.EmployeeResponse;
import com.jcr.salon.api.dto.response.ServiceResponse;
import com.jcr.salon.domain.entities.Appointment;
import com.jcr.salon.domain.entities.ClientEntity;
import com.jcr.salon.domain.entities.Employee;
import com.jcr.salon.domain.entities.ServiceEntity;
import com.jcr.salon.domain.repositories.AppointmentRepository;
import com.jcr.salon.domain.repositories.ClientRepository;
import com.jcr.salon.domain.repositories.EmployeeRepository;
import com.jcr.salon.domain.repositories.ServiceRepository;
import com.jcr.salon.infraestructure.abstract_services.IAppointmentService;
import com.jcr.salon.utils.enums.SortType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentService implements IAppointmentService {
  @Autowired
  private final ServiceRepository serviceRepository;
  @Autowired
  private final EmployeeRepository employeeRepository;
  @Autowired
  private final ClientRepository clientRepository;  
  @Autowired
  private final AppointmentRepository appointmentRepository;

  @Override
  public AppointmentResponse create(AppointmentRequest request) {
    ClientEntity client = this.clientRepository.findById(request.getClientId()).orElseThrow(() -> new RuntimeException("Client not found"));
    ServiceEntity service = this.serviceRepository.findById(request.getServiceId()).orElseThrow(() -> new RuntimeException("Service not found"));
    Employee employee = this.employeeRepository.findById(request.getEmployeeId()).orElseThrow(() -> new RuntimeException("Employee not found"));

    Appointment appointment = this.toEntity(request);
    appointment.setClient(client);
    appointment.setService(service);
    appointment.setEmployee(employee);

    

    return this.toResponse(this.appointmentRepository.save(appointment));

  }

  @Override
  public void delete(UUID id) {
    this.appointmentRepository.deleteById(id);
 ;
  }

  @Override
  public AppointmentResponse findById(UUID id) {
    
   return this.toResponse(this.find(id));
  }

  @Override
  public Page<AppointmentResponse> getAll(int page, int size, SortType sort) {
    if(page <0 ) page = 0;
    PageRequest pagination = null;

    switch (sort){
      case ASC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
      case DESC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
      case NONE -> pagination = PageRequest.of(page, size);
    }
    return this.appointmentRepository.findAll(pagination).map(this::toResponse);
  }

  @Override
  public AppointmentResponse update(AppointmentRequest request, UUID id) {
    //1. Lo busco en la base de datos
    Appointment appointment = this.find(id);

    //2. Obtener el cliente, servicio y empleado
    ClientEntity client = this.clientRepository.findById(request.getClientId()).orElseThrow(() -> new RuntimeException("Client not found"));

    Employee employee = this.employeeRepository.findById(request.getEmployeeId()).orElseThrow(() -> new RuntimeException("Employee not found"));

    ServiceEntity service = this.serviceRepository.findById(request.getServiceId()).orElseThrow(() -> new RuntimeException("Service not found"));

    appointment = this.toEntity(request);
    appointment.setClient(client);
    appointment.setService(service);
    appointment.setEmployee(employee);

    //Tengo que enviarle el id para que sepa que está actualizando el mismo
    appointment.setId(id);

    //3. Actualizo en la base de datos
    

    return this.toResponse(this.appointmentRepository.save(appointment));
    
  }
  public AppointmentResponse toResponse(Appointment entity) {
     ServiceResponse service = new ServiceResponse();
        BeanUtils.copyProperties(entity.getService(), service);

        EmployeeResponse employee = new EmployeeResponse();
        BeanUtils.copyProperties(entity.getEmployee(), employee);

        ClientBasicResponse client = new ClientBasicResponse();
        BeanUtils.copyProperties(entity.getClient(), client);

    return AppointmentResponse.builder()
        .id(entity.getId())
        .date(entity.getDate())
        .duration(entity.getDuration())
        .comment(entity.getComment())
        .service(service)
        .employee(employee)
        .client(client)
        .build();
  }

  public Appointment toEntity(AppointmentRequest request) {
   return Appointment.builder()
        .date(request.getDate())
        .duration(request.getDuration())
        .comment(request.getComment())
        .build();
  }

  private Appointment find(UUID id) {
    return this.appointmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Appointment not found"));
  }

}
