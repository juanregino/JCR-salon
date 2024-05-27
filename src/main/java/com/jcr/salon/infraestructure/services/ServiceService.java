package com.jcr.salon.infraestructure.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jcr.salon.api.dto.request.ServiceRequest;
import com.jcr.salon.api.dto.response.ServiceResponse;
import com.jcr.salon.domain.entities.ServiceEntity;
import com.jcr.salon.domain.repositories.ServiceRepository;
import com.jcr.salon.infraestructure.abstract_services.IServiceService;
import com.jcr.salon.utils.enums.SortType;
import com.jcr.salon.utils.exceptions.BadRequestException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceService implements IServiceService {

@Autowired 
private final ServiceRepository serviceRepository;  

  @Override
  public ServiceResponse create(ServiceRequest request) {
    ServiceEntity service = this.toEntity(request);
    
    return this.toResponse(this.serviceRepository.save(service));
   
  }

  @Override
  public void delete(UUID id) {
     this.serviceRepository.delete(this.find(id));
    
  }

  @Override
  public ServiceResponse findById(UUID id) {
    
    return this.toResponse(this.find(id));
  }

  @Override
  public Page<ServiceResponse> getAll(int page, int size, SortType sort) {
    if (page < 0 ) page = 0;
    PageRequest pagination = null;

    switch (sort) {
      case ASC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).ascending());
      case DESC -> pagination = PageRequest.of(page, size, Sort.by(FIELD_BY_SORT).descending());
      case NONE -> pagination = PageRequest.of(page, size);
    }

    return this.serviceRepository.findAll(pagination).map(this::toResponse);
    
  }

  @Override
  public ServiceResponse update(ServiceRequest request, UUID id) {  
    ServiceEntity service = this.find(id);
    service = this.toEntity(request);
    service.setId(id);
    return this.toResponse(this.serviceRepository.save(service));
  }

  private ServiceResponse toResponse(ServiceEntity entity){
    return  ServiceResponse.builder()
                            .id(entity.getId())
                            .name(entity.getName())
                            .description(entity.getDescription())
                            .price(entity.getPrice())
                            .build();
  }

  private ServiceEntity toEntity(ServiceRequest request){
    return ServiceEntity.builder()
                         .name(request.getName())
                         .description(request.getDescription())
                         .price(request.getPrice())
                         .build();
  }

  private ServiceEntity find(UUID id){
    return this.serviceRepository.findById(id).orElseThrow(() -> new BadRequestException("Service not found"));
  }
}
