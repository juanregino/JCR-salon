package com.jcr.salon.api.dto.response;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentBasicResponse {
  private UUID id;
  private String date;
  private Integer duration;
  private String comment;
  private ServiceResponse service;
  private EmployeeResponse employee;
}
