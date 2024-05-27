package com.jcr.salon.infraestructure.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jcr.salon.api.dto.request.EmployeeRequest;
import com.jcr.salon.api.dto.response.EmployeeResponse;
import com.jcr.salon.domain.entities.Employee;
import com.jcr.salon.domain.repositories.EmployeeRepository;
import com.jcr.salon.infraestructure.abstract_services.IEmployeeService;
import com.jcr.salon.utils.enums.SortType;
import com.jcr.salon.utils.exceptions.BadRequestException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService {
  @Autowired
  private final EmployeeRepository employeeRepository;

  @Override
  public EmployeeResponse create(EmployeeRequest request) {
    Employee employee = this.toEntity(request);

    return this.toResponse(this.employeeRepository.save(employee));
  }

  @Override
  public void delete(UUID id) {
   Employee employee = this.find(id);
   this.employeeRepository.delete(employee);
  }

  @Override
  public EmployeeResponse findById(UUID id) {
    
    return this.toResponse(this.find(id));
  }

  @Override
  public Page<EmployeeResponse> getAll(int page, int size, SortType sort) {
    if(page < 0 ) page = 0 ;
    PageRequest pagination = null;

    switch (sort) {
      case ASC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
      case DESC -> pagination = PageRequest.of(page, size , Sort.by(FIELD_BY_SORT).descending());
      case NONE -> pagination = PageRequest.of(page, size);
    }
    return this.employeeRepository.findAll(pagination).map(this::toResponse);
  }

  @Override
  public EmployeeResponse update(EmployeeRequest request, UUID id) {
    Employee employee = this.find(id);
    employee = this.toEntity(request);
    employee.setId(id);
    return this.toResponse(this.employeeRepository.save(employee));

    
  }

  private EmployeeResponse toResponse(Employee entity){
    return EmployeeResponse.builder()
                            .id(entity.getId())
                            .name(entity.getName())
                            .lastName(entity.getLastName())
                            .phone(entity.getPhone())
                            .email(entity.getEmail())
                            .role(entity.getRole())
                            .build(); 


  }

  private Employee toEntity (EmployeeRequest request){
    return Employee.builder()
                    .name(request.getName())
                    .lastName(request.getLastName())
                    .phone(request.getPhone())
                    .email(request.getEmail())
                    .role(request.getRole())
                    .build();
  }

  private Employee find(UUID id){
    return this.employeeRepository.findById(id).orElseThrow(() -> new BadRequestException("Employee not found"));
  }

}
