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

import com.jcr.salon.api.dto.request.AppointmentRequest;
import com.jcr.salon.api.dto.response.AppointmentResponse;
import com.jcr.salon.infraestructure.abstract_services.IAppointmentService;
import com.jcr.salon.utils.enums.SortType;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {
  
  @Autowired
  private final IAppointmentService appointmentService;

  @GetMapping
  public ResponseEntity<Page<AppointmentResponse>> getAll(
    @RequestParam(defaultValue = "1") int page, 
    @RequestParam(defaultValue = "10") int size, 
    @RequestHeader(required = false) SortType sort) { 
      if(Objects.isNull(sort)) sort = SortType.NONE;

      return ResponseEntity.ok(appointmentService.getAll(page - 1, size, sort));
   
    }

  @GetMapping("/{id}")
  public ResponseEntity<AppointmentResponse> getById(@PathVariable UUID id) {
    return ResponseEntity.ok(appointmentService.findById(id));
  }

  @PostMapping
  public ResponseEntity<AppointmentResponse> create (  @RequestBody AppointmentRequest request) {
   return ResponseEntity.ok(this.appointmentService.create(request));
  }

  @PutMapping("/{id}")
  public ResponseEntity<AppointmentResponse> update(@PathVariable UUID id, @RequestBody AppointmentRequest request) {
    return ResponseEntity.ok(this.appointmentService.update(request, id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    this.appointmentService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
