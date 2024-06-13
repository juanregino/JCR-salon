package com.jcr.salon.api.controllers;

import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jcr.salon.api.dto.request.ServiceRequest;
import com.jcr.salon.api.dto.response.ServiceResponse;
import com.jcr.salon.infraestructure.abstract_services.IServiceService;
import com.jcr.salon.utils.enums.SortType;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/services")
@RequiredArgsConstructor
public class ServiceController {
   @Autowired
   private final IServiceService serviceService;

   @GetMapping(path = "/public/get")
   public ResponseEntity<Page<ServiceResponse>> getAll(
    @RequestParam(defaultValue = "1") int page, 
    @RequestParam(defaultValue = "10")int size, 
    @RequestHeader(required = false) SortType sort) { 
      if(Objects.isNull(sort)) sort = SortType.NONE;

     return ResponseEntity.ok(serviceService.getAll(page - 1, size, sort));
   }
   @GetMapping("/{id}")
   public ResponseEntity<ServiceResponse> getById(@RequestParam UUID id) {
     return ResponseEntity.ok(this.serviceService.findById(id));
   }
   @PostMapping
   public ResponseEntity<ServiceResponse> create(@RequestBody ServiceRequest request) {
    return ResponseEntity.ok(this.serviceService.create(request));
   }

   @PutMapping("/{id}")
   public ResponseEntity<ServiceResponse> update(@PathVariable UUID id, @RequestBody ServiceRequest request) {
    return ResponseEntity.ok(this.serviceService.update(request, id));
   }

   @DeleteMapping("/{id}")
   public ResponseEntity<Void> delete(@RequestParam UUID id) {
    this.serviceService.delete(id);
    return ResponseEntity.noContent().build();
   }
}
