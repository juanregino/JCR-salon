package com.jcr.salon.api.dto.request;

import com.jcr.salon.utils.enums.RoleEmployee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data 
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {
  private String name;
  private String lastName;
  private String phone;
  private String email; 
  private RoleEmployee role;
  
}
