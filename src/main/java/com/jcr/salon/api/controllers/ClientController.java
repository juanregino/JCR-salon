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

import com.jcr.salon.api.dto.request.ClientRequest;
import com.jcr.salon.api.dto.response.ClientResponse;
import com.jcr.salon.infraestructure.abstract_services.IClientService;
import com.jcr.salon.utils.enums.SortType;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {
  @Autowired
  private final IClientService  clientService; 

  @GetMapping
  public ResponseEntity<Page<ClientResponse>> getAll(
    @RequestParam(defaultValue = "1") int page, 
    @RequestParam(defaultValue = "10")int size , 
    @RequestHeader(required = false) SortType sort) { 
      if( Objects.isNull(sort)) sort = SortType.NONE;
    return ResponseEntity.ok(clientService.getAll(page - 1, size, sort));
  }

  @GetMapping("/{id}")
  public ResponseEntity<ClientResponse> getById (@PathVariable UUID id ){
    return ResponseEntity.ok(this.clientService.findById(id));
  }

  @PostMapping
  public ResponseEntity<ClientResponse> create(@RequestBody ClientRequest request) {
    return ResponseEntity.ok(this.clientService.create(request));
  }

  @PutMapping("/{id}")
  public ResponseEntity<ClientResponse> update(@PathVariable UUID id, @RequestBody ClientRequest request) {
  return ResponseEntity.ok(this.clientService.update(request, id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> delete(@PathVariable UUID id) {
    this.clientService.delete(id);
    return ResponseEntity.noContent().build();
  }
}
