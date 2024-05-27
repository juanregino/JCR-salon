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

import com.jcr.salon.api.dto.request.EmployeeRequest;
import com.jcr.salon.api.dto.response.EmployeeResponse;
import com.jcr.salon.infraestructure.abstract_services.IEmployeeService;
import com.jcr.salon.utils.enums.SortType;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
  @Autowired
  private final IEmployeeService employeeService;

  @GetMapping
  public ResponseEntity<Page<EmployeeResponse>> getAll(
    @RequestParam(defaultValue = "1") int page, 
    @RequestParam(defaultValue = "10") int size,
    @RequestHeader(required = false) SortType sort) {
      if(Objects.isNull(sort)) sort = SortType.NONE;
    return ResponseEntity.ok(employeeService.getAll(page - 1, size, SortType.NONE));
  }

  @GetMapping("/{id}")
  public ResponseEntity<EmployeeResponse> getById(@RequestParam UUID id) {
    return ResponseEntity.ok(this.employeeService.findById(id));
  }

  @PostMapping
  public ResponseEntity<EmployeeResponse> create(@RequestBody EmployeeRequest request) {
  return ResponseEntity.ok(this.employeeService.create(request));
  }

  @PutMapping("/{id}")
  public ResponseEntity<EmployeeResponse> update(@PathVariable UUID id, @RequestBody EmployeeRequest request) {
    return ResponseEntity.ok(this.employeeService.update(request, id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@RequestParam UUID id) {
    this.employeeService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
