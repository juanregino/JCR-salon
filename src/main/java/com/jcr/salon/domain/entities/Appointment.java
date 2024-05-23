package com.jcr.salon.domain.entities;


import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Appointment {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  @Column(nullable = false)
  private LocalDateTime date;
  @Column(nullable = false)
  private Integer duration;
  @Lob //-> Para que el acepte texto mas largo
  private String comment; 
 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "employee_id" ,referencedColumnName = "id")
  private Employee employee;
 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "client_id" ,referencedColumnName = "id")
  private ClientEntity client;
 @ManyToOne(fetch = FetchType.LAZY)
 @JoinColumn(name = "service_id" ,referencedColumnName = "id")
  private ServiceEntity service;
}
