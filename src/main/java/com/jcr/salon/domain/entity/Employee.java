package com.jcr.salon.domain.entity;

import java.util.UUID;

import com.jcr.salon.utils.enums.RoleEmployee;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;
  @Column(length = 100, nullable = false)
  private String name;
  @Column(length = 100, nullable = false)

  private String lastName;
  @Column(length = 20)

  private String phone;
  @Column(length = 100, nullable = false)

  private String email;
  @Column(nullable = false, length = 50)
  @Enumerated(EnumType.STRING)
  private RoleEmployee role;

}
