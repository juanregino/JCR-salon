package com.jcr.salon.api.dto.response;

import java.util.UUID;

import com.jcr.salon.utils.enums.RoleEmployee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeResponse {
  private UUID id;
  private String name;
  private String lastName;
  private String phone;
  private String email;
  private RoleEmployee role;
}
